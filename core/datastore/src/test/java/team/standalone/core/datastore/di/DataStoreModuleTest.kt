package team.standalone.core.datastore.di

import android.content.Context
import app.cash.turbine.test
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.misusing.UnfinishedStubbingException
import team.standalone.core.datastore.serializer.SettingsProtoFileSerializer
import team.standalone.core.datastore.serializer.UserProtoFileSerializer

@ExperimentalCoroutinesApi
class DataStoreModuleTest {

    @Mock
    private lateinit var appContext: Context

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    /**
     * Test case: should be able to provide a settings proto if settings proto existing.
     * @assertions:
     *       assertThat: result is not null.
     * */
    @Test
    fun shouldBeAbleToProvideSettingsProtoDataStore() = runTest{
        DataStoreModule.providesSettingsProtoDataStore(
            appContext,
            UnconfinedTestDispatcher(),
            SettingsProtoFileSerializer()
        ).data.test {
            val result = awaitEvent()
            Truth.assertThat(result).isNotNull()
            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Test case: should not be able to provide a settings proto if exception is thrown.
     * @assertions:
     *       assertThat: exception is thrown.
     * */
    @Test
    fun shouldNotBeAbleToProvideSettingsProtoDataStoreWhenExceptionIsThrown() = runTest {
        Mockito.doThrow(UnfinishedStubbingException(""))
        DataStoreModule.providesSettingsProtoDataStore(
            appContext,
            UnconfinedTestDispatcher(),
            SettingsProtoFileSerializer()
        ).data.test {
            val result = awaitError()
            Truth
                .assertThat(result.javaClass)
                .isEqualTo(UnfinishedStubbingException("").javaClass)
            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Test case: should be able to provide a user proto if user proto existing.
     * @assertions:
     *       assertThat: result is not null.
     * */
    @Test
    fun shouldBeAbleToProvideUserProtoDataStore() = runTest {
        DataStoreModule.providesUserProtoDataStore(
            appContext,
            UnconfinedTestDispatcher(),
            UserProtoFileSerializer()
        ).data.test {
            val result = awaitEvent()
            Truth.assertThat(result).isNotNull()
            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Test case: should not be able to provide a user proto if exception is thrown.
     * @assertions:
     *       assertThat: exception is thrown.
     * */
    @Test
    fun shouldNotBeAbleToProvideUserProtoDataStoreWhenExceptionIsThrown() = runTest {
        Mockito.doThrow(UnfinishedStubbingException(""))
        DataStoreModule.providesUserProtoDataStore(
            appContext,
            UnconfinedTestDispatcher(),
            UserProtoFileSerializer()
        ).data.test {
            val result = awaitError()
            Truth
                .assertThat(result.javaClass)
                .isEqualTo(UnfinishedStubbingException("").javaClass)
            cancelAndConsumeRemainingEvents()
        }
    }
}