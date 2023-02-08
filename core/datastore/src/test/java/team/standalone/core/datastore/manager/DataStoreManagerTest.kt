package team.standalone.core.datastore.manager

import androidx.datastore.core.DataStore
import app.cash.turbine.test
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import team.standalone.core.datastore.proto.model.SettingsProtoFile
import team.standalone.core.datastore.proto.model.UserProtoFile

@ExperimentalCoroutinesApi
class DataStoreManagerTest {

    private lateinit var dataStoreManager: DataStoreManager

    @Mock
    private lateinit var userProtoDataStore: DataStore<UserProtoFile>

    @Mock
    private lateinit var settingsProtoDataStore: DataStore<SettingsProtoFile>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        dataStoreManager = DataStoreManager(
            userProtoDataStore,
            settingsProtoDataStore
        )
    }

    /**
     * Test case: should be able to receive a user proto if user proto existing from a flow.
     * @assertions:
     *       assertThat: result is not null.
     * */
    @Test
    fun shouldBeAbleToReceiveAUserProto() = runTest {
        dataStoreManager
            .getUserProto()
            .test {
                val result = awaitItem()
                Truth.assertThat(result).isNotNull()
                cancelAndConsumeRemainingEvents()
            }
    }

    /**
     * Test case: should not be able to receive a user proto if data is null
     * @assertions:
     *       assertThat: result is empty.
     * */
    @Test
    fun shouldNotBeAbleToReceiveAUserProtoWhenDataIsNull() = runTest {
        val expected = ""
        Mockito
            .`when`(userProtoDataStore.data)
            .thenReturn(null)
        dataStoreManager
            .getUserProto()
            .test {
                val result = awaitItem()
                Truth.assertThat(result.uid).isEqualTo(expected)
                Truth.assertThat(result.email).isEqualTo(expected)
                Truth.assertThat(result.isEmailVerified).isEqualTo(false)
                cancelAndConsumeRemainingEvents()
            }
    }

    /**
     * Test case: should be able to receive a settings proto if settings proto existing from a flow.
     * @assertions:
     *       assertThat: result is not null.
     * */
    @Test
    fun shouldBeAbleToReceiveASettingsProto() = runTest {
        dataStoreManager
            .getSettingsProto()
            .test {
                val result = awaitItem()
                Truth.assertThat(result).isNotNull()
                cancelAndConsumeRemainingEvents()
            }
    }

    /**
     * Test case: should not be able to receive a settings proto if data is null
     * @assertions:
     *       assertThat: result is empty.
     * */
    @Test
    fun shouldNotBeAbleToReceiveASettingsProtoWhenDataIsNull() = runTest {
        val expected = ""
        Mockito
            .`when`(settingsProtoDataStore.data)
            .thenReturn(null)
        dataStoreManager
            .getSettingsProto()
            .test {
                val result = awaitItem()
                Truth
                    .assertThat(result.chatLanguage)
                    .isEqualTo(expected)
                cancelAndConsumeRemainingEvents()
            }
    }
}