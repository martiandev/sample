package team.standalone.core.database.datasource.user

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
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.database.testutil.fake.FakeUserDao
import team.standalone.core.database.testutil.UserTest

@RunWith(AndroidJUnit4::class)
@MediumTest
class UserLocalDataSourceImplTest: UserTest(){

    lateinit var dataSourceImpl: UserLocalDataSource
    lateinit var fakeUserDao: FakeUserDao
    lateinit var ioDispatcher: CoroutineDispatcher

    @Before
    fun setUp(){
        fakeUserDao = FakeUserDao()
        ioDispatcher = UnconfinedTestDispatcher()
        dataSourceImpl = UserLocalDataSourceImpl(
            ioDispatcher,
            fakeUserDao
        )
    }


    /**
     * Test success scenario for getting UserEntity using uid
     * Expected - result data should not be null
     */
    @Test
    fun shouldBeAbleToGetAnExistingUser() = runTest {
        Truth.assertThat(
            dataSourceImpl
                .getUser("test")
                .asLoadResult()
                .data
        ).isNotNull()
    }

    /**
     * Test fail scenario for getting UserEntity using uid
     * Expected - result data should be null
     */
    @Test
    fun shouldBeAbleToReceiveNullForNonExistingUser() = runTest {
        Truth.assertThat(
            dataSourceImpl
                .getUser("test2")
                .asLoadResult()
                .data
        ).isNull()
    }

    /**
     * Test success scenario for getting UserEntity as a flow using uid
     * Expected - result data should not be null
     */
    @Test
    fun shouldBeAbleToReceiveUserIfUserExistsFromAFlow() = runTest{
        dataSourceImpl
            .getUserAsFlow("test")
            .test {
                var result = awaitItem()
                Truth.assertThat(result)
                    .isNotNull()
                cancelAndConsumeRemainingEvents()
            }
    }

    /**
     * Test fail scenario for getting UserEntity as a flow using uid
     * Expected - result data should be null
     */
    @Test
    fun shouldBeAbleToReceiveNulltIfUserDoesNotExistsFromAFlow() = runTest {
        dataSourceImpl
            .getUserAsFlow("test2")
            .test {
                var result = awaitItem()
                Truth.assertThat(result)
                    .isNull()
                cancelAndConsumeRemainingEvents()
            }
    }


    /**
     * Test success scenario for inserting UserEntity
     * Expected - Success Result
     */
    @Test
    fun shouldBeAbleToInsertOrUpdateAnUserEntityAndReceiveSuccessResult() = runTest {
        var result = dataSourceImpl.saveUser(
            getTestUser(1)
        )
        when(result){
            is Result.Success -> assert(true)
            is Result.Error -> assert(false)
        }
    }

    /**
     * Test fail scenario for inserting UserEntity
     * Expected - Success Error
     */
    @Test
    fun shouldBeAbleReceiveAnErrorResultWhenEncounteredAnErrorWhileSavingAUser() = runTest {
        var result = dataSourceImpl.saveUser(
            getTestUser(2)
        )
        when(result){
            is Result.Success -> assert(false)
            is Result.Error -> assert(true)
        }
    }


    /**
     * Test success scenario for deleting UserEntity
     * Expected - Success Result
     */
    @Test
    fun deleteUserShouldSucceedWhenUserIsExisting() = runTest{
        var result = dataSourceImpl.deleteUser("test1")
        when(result){
            is Result.Success -> assert(true)
            is Result.Error -> assert(false)
        }
    }

    /**
     * Test fail scenario for deleting UserEntity
     * Expected - Success Error
     */
    @Test
    fun deleteUserShouldThrowAnErrorWhenUserIsNotExisting() = runTest{
        var result = dataSourceImpl.deleteUser("test2")
        when(result){
            is Result.Success -> assert(false)
            is Result.Error -> assert(true)
        }
    }

    /**
     * Test success scenario for deleting all UserEntity
     * Expected - Success Result
     */
    @Test
    fun shouldReceiveASuccessResultWhenDeletingAllUsers() = runTest{
        var result = dataSourceImpl.deleteAll()
        when(result){
            is Result.Success -> assert(true)
            is Result.Error -> assert(false)
        }
    }

}