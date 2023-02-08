package team.standalone.core.database.room.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import app.cash.turbine.test
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import team.standalone.core.database.testutil.UserTest
import team.standalone.core.database.room.AppDatabase

@RunWith(AndroidJUnit4::class)
@MediumTest
class UserDaoTest: UserTest(){
    private lateinit var database: AppDatabase
    private lateinit var dao: UserDao

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
        dao = database.userDao()
    }

    /**
     * Closes database
     */
    @After
    fun tearDown(){
        database.close()
    }



    /**
     * Test if dao is able to save an UserEntity
     */
    @Test
    fun shouldBeAbleToInsertAUser() = runTest {
        var user = getTestUser(1)
        dao.insert(user)
        dao.getAsFlow(user.uid).test {
            val emission = awaitItem()
            Truth.assertThat(emission)
                .isEqualTo(user)
        }
    }


    /**
     * Test if dao is able to save a collection of UserEntity
     */
    @Test
    fun shouldBeAbleToInsertUsers() = runTest {

        var artists = generateListOfTestUsers(4)
        Truth.assertThat(
            dao.insert(
                artists.get(0),
                artists.get(1),
                artists.get(2),
                artists.get(3),
            ).size
        )
            .isEqualTo(4)
        artists.forEach(){ artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(artist)
            }
        }
    }

    /**
     * Test if dao is able to save a list of UserEntity
     */
    @Test
    fun shouldBeAbleToInsertUsersList() = runTest {

        var users = generateListOfTestUsers(4)
        Truth.assertThat(dao.insert(users).size)
            .isEqualTo(4)
        users.forEach(){ user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(user)
            }
        }
    }

    /**
     * Overwrite an existing UserEntity
     */
    @Test
    fun shouldBeAbleToUpdateExistingUser() = runTest {
        var user = getTestUser(1)
        var update = getUpdatedUser(1)
        dao.insertOrReplace(user)
        dao.getAsFlow(user.uid).test {
            val emission = awaitItem()
            if (emission != null) {
                Truth.assertThat(emission)
                    .isEqualTo(user)
            }
        }
        dao.insertOrReplace(update)
        dao.getAsFlow(user.uid).test {
            val emission = awaitItem()
            if (emission != null) {
                Truth.assertThat(emission)
                    .isEqualTo(update)
            }

        }
    }

    /**
     * Test if dao is able to overwrite a collection of UserEntity
     */
    @Test
    fun shouldBeAbleToUpdateExistingUsers() = runTest {

        var users = generateListOfTestUsers(4)
        var updatedUsers = generateListOfUpdatedTestUsers(4)

        Truth.assertThat(
            dao.insertOrReplace(
                users.get(0),
                users.get(1),
                users.get(2),
                users.get(3)
            ).size
        )
            .isEqualTo(4)
        users.forEach(){ user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(user)
            }
        }

        Truth.assertThat(
            dao.insertOrReplace(
                updatedUsers.get(0),
                updatedUsers.get(1),
                updatedUsers.get(2),
                updatedUsers.get(3)
            ).size
        )
            .isEqualTo(4)
        users.forEachIndexed(){ index, user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(updatedUsers.get(index))
            }
        }

    }

    /**
     * Test if dao is able to overwrite a list of UserEntity
     */
    @Test
    fun shouldBeAbleToUpdateExistingUserList() = runTest {

        var users = generateListOfTestUsers(4)
        var updatedUsers = generateListOfUpdatedTestUsers(4)

        Truth.assertThat(dao.insertOrReplace(users).size)
            .isEqualTo(4)
        users.forEach(){ user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(user)
            }
        }

        Truth.assertThat(dao.insertOrReplace(updatedUsers).size)
            .isEqualTo(4)
        users.forEachIndexed(){ index, user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(updatedUsers.get(index))
            }
        }

    }

    /**
     * Should be able to ignore changes on Existing UserEntity
     */
    @Test
    fun shouldBeAbleToIgnoreChangesToAUser() = runTest {
        var user = getTestUser(1)
        var updated = getUpdatedUser(1)
        dao.insertOrIgnore(user)
        dao.getAsFlow(user.uid).test {
            val emission = awaitItem()
            if (emission != null) {
                Truth.assertThat(emission)
                    .isEqualTo(user)
            }
        }
        dao.insertOrIgnore(updated)
        dao.getAsFlow(user.uid).test {
            val emission = awaitItem()
            if (emission != null) {
                Truth.assertThat(emission)
                    .isEqualTo(user)
            }

        }
    }

    /**
     * Test if dao is able to ignore changes for a collection of existing UserEntity
     */
    @Test
    fun shouldBeAbleToIgnoreChangesToUsers() = runTest {

        var users = generateListOfTestUsers(4)
        var updated = generateListOfUpdatedTestUsers(4)

        Truth.assertThat(
            dao.insertOrIgnore(
                users.get(0),
                users.get(1),
                users.get(2),
                users.get(3)
            ).size
        )
            .isEqualTo(4)
        users.forEach(){ user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(user)
            }
        }

        Truth.assertThat(
            dao.insertOrIgnore(
                updated.get(0),
                updated.get(1),
                updated.get(2),
                updated.get(3)
            ).size
        )
            .isEqualTo(4)
        users.forEachIndexed(){ index, user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(users.get(index))
            }
        }

    }

    /**
     * Test if dao is able to ignore changes for a list of  existing UserEntity
     */
    @Test
    fun shouldBeAbleToIgnoreChangesToUsersList() = runTest {

        var users = generateListOfTestUsers(4)
        var updated = generateListOfUpdatedTestUsers(4)

        Truth.assertThat(dao.insertOrIgnore(users).size)
            .isEqualTo(4)
        users.forEach(){ user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(user)
            }
        }

        Truth.assertThat(dao.insertOrIgnore(updated).size)
            .isEqualTo(4)
        users.forEachIndexed(){ index, user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(users.get(index))
            }
        }

    }

    /**
     * Test if dao is able to update an UserEntity
     */
    @Test
    fun shouldBeAbleToUpdateUser() = runTest {
        var user = getTestUser(1)
        dao.insert(user)
        dao.getAsFlow(user.uid)
            .test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(user)
            }
        var updated = getUpdatedUser(1)
        dao.update(updated)
        dao.getAsFlow(user.uid)
            .test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(updated)
            }
    }

    /**
     * Test if dao is able to update a collection of UserEntity
     */
    @Test
    fun shouldBeAbleToUpdateUsers() = runTest {
        var users = generateListOfTestUsers(4)
        var updated = generateListOfUpdatedTestUsers(4)
        dao.insert(
            users.get(0),
            users.get(1),
            users.get(2),
            users.get(3),
        )
        users.forEach(){ user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(user)
            }
        }
        dao.update(
            updated.get(0),
            updated.get(1),
            updated.get(2),
            updated.get(3)
        )
        users.forEachIndexed(){ index, user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(updated.get(index))
            }
        }

    }

    /**
     * Test if dao is able to update a collection of UserEntity
     */
    @Test
    fun shouldBeAbleToUpdateUsersList() = runTest {
        var users = generateListOfTestUsers(4)
        var updated = generateListOfUpdatedTestUsers(4)
        dao.insert(
            users
        )
        users.forEach(){ user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(user)
            }
        }
        dao.update(
            updated
        )
        users.forEachIndexed(){ index, user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(updated.get(index))
            }
        }

    }

    /**
     * Test if dao is able to delete an UserEntity
     */
    @Test
    fun shouldBeAbleToDeleteAUser() = runTest {
        var user = getTestUser(1)
        dao.insert(user)
        dao.getAsFlow(user.uid)
            .test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(user)
            }
        dao.delete(user)
        dao.getAsFlow(user.uid)
            .test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isNull()
            }
    }

    /**
     * Test if dao is able to delete a collection of UserEntity
     */
    @Test
    fun shouldBeAbleToDeleteUsers() = runTest {
        var users = generateListOfTestUsers(4)
        dao.insert(
            users.get(0),
            users.get(1),
            users.get(2),
            users.get(3),
        )
        users.forEach(){ user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(user)
            }
        }
        dao.delete(
            users.get(0),
            users.get(1),
            users.get(2),
            users.get(3),
        )
        users.forEachIndexed(){ index, user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isNull()
            }
        }

    }

    /**
     * Test if dao is able to delete a list of UserEntity
     */
    @Test
    fun shouldBeAbleToDeleteUsersList() = runTest {
        var users = generateListOfTestUsers(4)
        dao.insert(users)
        users.forEach(){ artist ->
            dao.getAsFlow(artist.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(artist)
            }
        }
        dao.delete(
            users
        )
        users.forEachIndexed(){ index, user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isNull()
            }
        }

    }


    /**
     * Test if dao is able to upsert an UserEntity
     */
    @Test
    fun shouldBeAbleToUpSertAUser() = runTest {
        var user = getTestUser(1)
        dao.upsert(
            user
        )
        dao.getAsFlow(user.uid).test {
            val emission = awaitItem()
            Truth.assertThat(emission)
                .isEqualTo(user)
        }
        var update = getUpdatedUser(1)
        dao.upsert(
            update
        )
        dao.getAsFlow(user.uid).test {
            val emission = awaitItem()
            Truth.assertThat(emission)
                .isEqualTo(update)
        }
    }

    /**
     * Test if dao is able to upsert a collection of UserEntity
     */
    @Test
    fun shouldBeAbleToUpSertUsers() = runTest {
        var users = generateListOfTestUsers(4)
        var updated = generateListOfUpdatedTestUsers(4)
        dao.upsert(
            users.get(0),
            users.get(1),
            users.get(2),
            users.get(3),
        )

        users.forEachIndexed(){ index, user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(users.get(index))
            }
        }

        dao.upsert(
            updated.get(0),
            updated.get(1),
            updated.get(2),
            updated.get(3),
        )
        users.forEachIndexed(){ index, user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(updated.get(index))
            }
        }
    }


    /**
     * Test if dao is able to upsert a list of UserEntity
     */
    @Test
    fun shouldBeAbleToUpSertUserList() = runTest {
        var users = generateListOfTestUsers(4)
        var updated = generateListOfUpdatedTestUsers(4)
        dao.upsert(
            users
        )

        users.forEachIndexed(){ index, user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(users.get(index))
            }
        }

        dao.upsert(
            updated
        )

        users.forEachIndexed(){ index, user ->
            dao.getAsFlow(user.uid).test {
                val emission = awaitItem()
                Truth.assertThat(emission)
                    .isEqualTo(updated.get(index))
            }
        }
    }


    /**
     * Test if dao is able retrieve an UserEntity with
     * matching uid
     */
    @Test
    fun shouldBeAbleToRetrieveAUserUsingUid() = runTest {
        var user = getTestUser(1)
        dao.insert(user)
        var retrievedUser = dao.get(user.uid)
        Truth.assertThat(retrievedUser)
            .isNotNull()
    }

    /**
     * Retrieve UserEntity as a flow
     */
    @Test
    fun shouldBeAbleToRetrieveAUser() = runTest {
        var user = getTestUser(1)
        dao.insert(user)
        dao.getAsFlow(user.uid).test {
            val emission = awaitItem()
            Truth.assertThat(emission)
                .isEqualTo(user)
        }
    }

    /**
     * Delete an UserEntity by providing its uid
     */
    @Test
    fun shouldBeAbleToDeleteAUserUsingUid() = runTest {
        var user = getTestUser(1)
        dao.insert(user)
        dao.delete(user.uid)
        dao.getAsFlow(user.uid).test {
            val emission = awaitItem()
            Truth.assertThat(emission)
                .isNull()
        }
    }

    /**
     * Delete all UserEntity
     */
    @Test
    fun shouldBeAbleToDeleteAllUsers() = runTest {
        var user1 = getTestUser(1)
        var user2 = getTestUser(2)
        dao.insert(user1)
        dao.insert(user2)
        Truth.assertThat(dao.deleteAll())
            .isEqualTo(2)
        dao.getAsFlow(user1.uid).test {
            val emission = awaitItem()
            Truth.assertThat(emission)
                .isNull()
        }
        dao.getAsFlow(user2.uid).test {
            val emission = awaitItem()
            Truth.assertThat(emission)
                .isNull()
        }

    }



}