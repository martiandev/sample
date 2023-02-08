package team.standalone.core.database.datasource.artist

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import app.cash.turbine.test
import com.google.common.truth.Truth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import team.standalone.core.common.util.Result
import team.standalone.core.database.testutil.ArtistTest
import team.standalone.core.database.testutil.fake.FakeArtistDao

@RunWith(AndroidJUnit4::class)
@MediumTest
class ArtistLocalDataSourceImplTest: ArtistTest(){

    lateinit var dataSourceImpl: ArtistLocalDataSource
    lateinit var fakeArtistDao: FakeArtistDao
    lateinit var ioDispatcher: CoroutineDispatcher

    @Before
    fun setUp(){
        fakeArtistDao = FakeArtistDao()
        ioDispatcher = UnconfinedTestDispatcher()
        dataSourceImpl = ArtistLocalDataSourceImpl(
            ioDispatcher,
            fakeArtistDao
        )
    }

    /**
     * Test success scenario for getting ArtistEntity using uid
     * Expected - result data should not be null
     */
    @Test
    fun shouldBeAbleToGetAnExistingArtist() = runTest {

        Truth.assertThat(
            dataSourceImpl
                .getArtist("test")
        ).isNotNull()
    }

    /**
     * Test fail scenario for getting ArtistEntity using uid
     * Expected - result data should be null
     */
    @Test
    fun shouldBeAbleToReceiveNullForNonExistingArtist() = runTest {
        Truth.assertThat(
            dataSourceImpl
                .getArtist("test2")
        ).isNotNull()
    }

    /**
     * Test success scenario for getting ArtistEntity as a flow using uid
     * Expected - result data should not be null
     */
    @Test
    fun shouldBeAbleToReceiveAnArtistIfArtistExistsFromAFlow() = runTest{
        dataSourceImpl
            .getArtistAsFlow("test")
            .test {
                var result = awaitItem()
                Truth.assertThat(result)
                    .isNotNull()
                cancelAndConsumeRemainingEvents()
            }
    }


    /**
     * Test fail scenario for getting ArtistEnity as a flow using uid
     * Expected - result data should be null
     */
    @Test
    fun shouldBeAbleToReceiveNulltIfArtistDoesNotExistsFromAFlow() = runTest {
        dataSourceImpl
            .getArtistAsFlow("test1")
            .test {
                var result = awaitItem()
                Truth.assertThat(result)
                    .isNull()
                cancelAndConsumeRemainingEvents()

            }
    }

    /**
     * Test success scenario for inserting ArtistEnity
     * Expected - Success Result
     */
    @Test
    fun shouldBeAbleToInsertOrUpdateAnArtistEntityAndReceiveSuccessResult() = runTest {
        var result = dataSourceImpl.saveArtist(
            getTestArtist(1)
        )
        when(result){
            is Result.Success -> assert(true)
            is Result.Error -> assert(false)
        }
    }

    /**
     * Test fail scenario for inserting ArtistEntity
     * Expected - Success Error
     */
    @Test
    fun shouldBeAbleReceiveAnErrorResultWhenEncounteredAnErrorWhileSavingAnArtist() = runTest {
        var result = dataSourceImpl.saveArtist(
            getTestArtist(2)
        )
        when(result){
            is Result.Success -> assert(false)
            is Result.Error -> assert(true)
        }
    }

}