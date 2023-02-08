package team.standalone.core.database.room.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import team.standalone.core.database.testutil.ArtistTest
import team.standalone.core.database.room.AppDatabase

@RunWith(AndroidJUnit4::class)
@MediumTest
class ArtistDaoTest : ArtistTest() {

    private lateinit var database: AppDatabase
    private lateinit var dao: ArtistDao

    /**
     * Setup database and dao instance
     */
    @Before
    fun setUp(){
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase::class.java,
            ).allowMainThreadQueries()
            .build()
        dao = database.artistDao()
    }

    /**
     * Closes database
     */
    @After
    fun tearDown(){
        database.close()
    }


    /**
     * Test if dao is able to save an ArtistEntity
     */
    @Test
     fun shouldBeAbleToInsertAnArtist() = runTest {
        var artist = getTestArtist(1)
        dao.insert(artist)
        dao.getAsFlow(artist.uid).test {
            val emission = awaitItem()
            assertThat(emission)
                .isEqualTo(artist)
        }
     }

    /**
     * Test if dao is able to save a collection of ArtistEntity
     */
    @Test
    fun shouldBeAbleToInsertArtists() = runTest {

        var artists = generateListOfTestArtists(4)
        assertThat(dao.insert(
            artists.get(0),
            artists.get(1),
            artists.get(2),
            artists.get(3),
        ).size)
            .isEqualTo(4)
        artists.forEach(){ artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artist)
            }
        }
    }

    /**
     * Test if dao is able to save a list of ArtistEntity
     */
    @Test
    fun shouldBeAbleToInsertArtistsList() = runTest {

        var artists = generateListOfTestArtists(4)
        assertThat(dao.insert(artists).size)
            .isEqualTo(4)
        artists.forEach(){ artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artist)
            }
        }
    }

    /**
     * Overwrite an existing ArtistEntity
     */
    @Test
    fun shouldBeAbleToUpdateExistingArtist() = runTest {
        var artist = getTestArtist(1)
        var artist2 = getUpdatedArtist(1)
        dao.insertOrReplace(artist)
        dao.getAsFlow(artist.uid).test {
            val emission = awaitItem()
            if (emission != null) {
                assertThat(emission)
                    .isEqualTo(artist)
            }
        }
        dao.insertOrReplace(artist2)
        dao.getAsFlow(artist.uid).test {
            val emission = awaitItem()
            if (emission != null) {
                assertThat(emission)
                    .isEqualTo(artist2)
            }

        }
    }
    /**
     * Test if dao is able to overwrite a collection of ArtistEntity
     */
    @Test
    fun shouldBeAbleToUpdateExistingArtists() = runTest {

        var artists = generateListOfTestArtists(4)
        var updatedArtists = generateListOfUpdatedTestArtists(4)

        assertThat(dao.insertOrReplace(
            artists.get(0),
            artists.get(1),
            artists.get(2),
            artists.get(3)
        ).size)
            .isEqualTo(4)
        artists.forEach(){ artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artist)
            }
        }

        assertThat(dao.insertOrReplace(
            updatedArtists.get(0),
            updatedArtists.get(1),
            updatedArtists.get(2),
            updatedArtists.get(3)
        ).size)
            .isEqualTo(4)
        artists.forEachIndexed(){ index, artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(updatedArtists.get(index))
            }
        }

    }


    /**
     * Test if dao is able to overwrite a list of ArtistEntity
     */
    @Test
    fun shouldBeAbleToUpdateExistingArtistsList() = runTest {

        var artists = generateListOfTestArtists(4)
        var updatedArtists = generateListOfUpdatedTestArtists(4)

        assertThat(dao.insertOrReplace(artists).size)
            .isEqualTo(4)
        artists.forEach(){ artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artist)
            }
        }

        assertThat(dao.insertOrReplace(updatedArtists).size)
            .isEqualTo(4)
        artists.forEachIndexed(){ index, artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(updatedArtists.get(index))
            }
        }

    }

    /**
     * Should be able to ignore changes on Existing ArtistEntity
     */
    @Test
    fun shouldBeAbleToIgnoreChangesToAnArtist() = runTest {
        var artist = getTestArtist(1)
        var artist2 = getUpdatedArtist(1)
        dao.insertOrIgnore(artist)
        dao.getAsFlow(artist.uid).test {
            val emission = awaitItem()
            if (emission != null) {
                assertThat(emission)
                    .isEqualTo(artist)
            }
        }
        dao.insertOrIgnore(artist2)
        dao.getAsFlow(artist.uid).test {
            val emission = awaitItem()
            if (emission != null) {
                assertThat(emission)
                    .isEqualTo(artist)
            }

        }
    }

    /**
     * Test if dao is able to ignore changes for a collection of existing ArtistEntity
     */
    @Test
    fun shouldBeAbleToIgnoreChangesToArtists() = runTest {

        var artists = generateListOfTestArtists(4)
        var updatedArtists = generateListOfUpdatedTestArtists(4)

        assertThat(dao.insertOrIgnore(
            artists.get(0),
            artists.get(1),
            artists.get(2),
            artists.get(3)
        ).size)
            .isEqualTo(4)
        artists.forEach(){ artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artist)
            }
        }

        assertThat(dao.insertOrIgnore(
            updatedArtists.get(0),
            updatedArtists.get(1),
            updatedArtists.get(2),
            updatedArtists.get(3)
        ).size)
            .isEqualTo(4)
        artists.forEachIndexed(){ index, artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artists.get(index))
            }
        }

    }


    /**
     * Test if dao is able to ignore changes for a list of  existing ArtistEntity
     */
    @Test
    fun shouldBeAbleToIgnoreChangesToArtistsList() = runTest {

        var artists = generateListOfTestArtists(4)
        var updatedArtists = generateListOfUpdatedTestArtists(4)

        assertThat(dao.insertOrIgnore(artists).size)
            .isEqualTo(4)
        artists.forEach(){ artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artist)
            }
        }

        assertThat(dao.insertOrIgnore(updatedArtists).size)
            .isEqualTo(4)
        artists.forEachIndexed(){ index, artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artists.get(index))
            }
        }

    }

    /**
     * Test if dao is able to update an ArtistEntity
     */
    @Test
    fun shouldBeAbleToUpdateArtist() = runTest {
        var artist = getTestArtist(1)
        dao.insert(artist)
        dao.getAsFlow(artist.uid)
            .test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artist)
            }
        var updatedArtist = getUpdatedArtist(1)
        dao.update(updatedArtist)
        dao.getAsFlow(artist.uid)
            .test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(updatedArtist)
            }
    }

    /**
     * Test if dao is able to update a collection of ArtistEntity
     */
    @Test
    fun shouldBeAbleToUpdateArtists() = runTest {
        var artists = generateListOfTestArtists(4)
        var updates = generateListOfUpdatedTestArtists(4)
        dao.insert(
            artists.get(0),
            artists.get(1),
            artists.get(2),
            artists.get(3),
        )
        artists.forEach(){ artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artist)
            }
        }
        dao.update(
            updates.get(0),
            updates.get(1),
            updates.get(2),
            updates.get(3)
        )
        artists.forEachIndexed(){ index, artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(updates.get(index))
            }
        }

    }
    /**
     * Test if dao is able to update a collection of ArtistEntity
     */
    @Test
    fun shouldBeAbleToUpdateArtistsList() = runTest {
        var artists = generateListOfTestArtists(4)
        var updates = generateListOfUpdatedTestArtists(4)
        dao.insert(
           artists
        )
        artists.forEach(){ artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artist)
            }
        }
        dao.update(
           updates
        )
        artists.forEachIndexed(){ index, artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(updates.get(index))
            }
        }

    }

    /**
     * Test if dao is able to delete an ArtistEntity
     */
    @Test
    fun shouldBeAbleToDeleteAnArtist() = runTest {
        var artist = getTestArtist(1)
        dao.insert(artist)
        dao.getAsFlow(artist.uid)
            .test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artist)
            }
        dao.delete(artist)
        dao.getAsFlow(artist.uid)
            .test {
                val emission = awaitItem()
                assertThat(emission)
                    .isNull()
            }
    }

    /**
     * Test if dao is able to delete a collection of ArtistEntity
     */
    @Test
    fun shouldBeAbleToDeleteArtists() = runTest {
        var artists = generateListOfTestArtists(4)
        dao.insert(
            artists.get(0),
            artists.get(1),
            artists.get(2),
            artists.get(3),
        )
        artists.forEach(){ artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artist)
            }
        }
        dao.delete(
            artists.get(0),
            artists.get(1),
            artists.get(2),
            artists.get(3),
        )
        artists.forEachIndexed(){ index, artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isNull()
            }
        }

    }

    /**
     * Test if dao is able to delete a list of ArtistEntity
     */
    @Test
    fun shouldBeAbleToDeleteArtistsList() = runTest {
        var artists = generateListOfTestArtists(4)
        dao.insert(artists)
        artists.forEach(){ artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artist)
            }
        }
        dao.delete(
           artists
        )
        artists.forEachIndexed(){ index, artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isNull()
            }
        }

    }


    /**
     * Test if dao is able to upsert an ArtistEntity
     */
    @Test
    fun shouldBeAbleToUpSertAnArtist() = runTest {
        var artist = getTestArtist(1)
        dao.upsert(
            artist
        )
        dao.getAsFlow(artist.uid).test {
            val emission = awaitItem()
            assertThat(emission)
                .isEqualTo(artist)
        }
        var update = getUpdatedArtist(1)
        dao.upsert(
            update
        )
        dao.getAsFlow(artist.uid).test {
            val emission = awaitItem()
            assertThat(emission)
                .isEqualTo(update)
        }
    }

    /**
     * Test if dao is able to upsert a collection of ArtistEntity
     */
    @Test
    fun shouldBeAbleToUpSertArtists() = runTest {
        var artists = generateListOfTestArtists(4)
        var updates = generateListOfUpdatedTestArtists(4)
        dao.upsert(
            artists.get(0),
            artists.get(1),
            artists.get(2),
            artists.get(3),
        )

        artists.forEachIndexed(){ index, artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artists.get(index))
            }
        }

        dao.upsert(
            updates.get(0),
            updates.get(1),
            updates.get(2),
            updates.get(3),
        )
        artists.forEachIndexed(){ index, artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(updates.get(index))
            }
        }


    }

    /**
     * Test if dao is able to upsert a list of ArtistEntity
     */
    @Test
    fun shouldBeAbleToUpSertArtistsList() = runTest {
        var artists = generateListOfTestArtists(4)
        var updates = generateListOfUpdatedTestArtists(4)
        dao.upsert(
            artists
        )

        artists.forEachIndexed(){ index, artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(artists.get(index))
            }
        }

        dao.upsert(
           updates
        )
        artists.forEachIndexed(){ index, artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                assertThat(emission)
                    .isEqualTo(updates.get(index))
            }
        }
    }

    /**
     * Test if dao is able retrieve an ArtistEntity with
     * matching uid
     */
    @Test
    fun shouldBeAbleToRetrieveArtistAnUsingUid() = runTest {
        var artist = getTestArtist(1)
        dao.insert(artist)
        var retrievedArtist = dao.get(artist.uid)
        assertThat(retrievedArtist)
            .isNotNull()
    }

    /**
     * Retrieve ArtistEntity as a flow
     */
    @Test
    fun shouldBeAbleToRetrieveAnArtist() = runTest {
        var artist = getTestArtist(1)
        dao.insert(artist)
        dao.getAsFlow(artist.uid).test {
            val emission = awaitItem()
            assertThat(emission)
                .isEqualTo(artist)
        }
    }

    /**
     * Delete an ArtistEntity by providing its uid
     */
    @Test
    fun shouldBeAbleToDeleteAnArtistUsingUid() = runTest {
        var artist = getTestArtist(1)
        dao.insert(artist)
        dao.delete(artist.uid)
        dao.getAsFlow(artist.uid).test {
            val emission = awaitItem()
            assertThat(emission)
                .isNull()
        }
    }

    /**
     * Delete all ArtistEntity
     */
    @Test
    fun shouldBeAbleToDeleteAllArtists() = runTest {
        var artist = getTestArtist(1)
        var artist2 = getTestArtist(2)
        dao.insert(artist)
        dao.insert(artist2)
        assertThat(dao.deleteAll())
            .isEqualTo(2)
        dao.getAsFlow(artist.uid).test {
            val emission = awaitItem()
            assertThat(emission)
                .isNull()
        }
        dao.getAsFlow(artist2.uid).test {
            val emission = awaitItem()
            assertThat(emission)
                .isNull()
        }

    }


}