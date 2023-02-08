package team.standalone.core.network.datasource.chat

import android.webkit.MimeTypeMap
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import team.standalone.core.common.extension.empty
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.network.util.test.ChatDataSourceTestUtil
import java.io.File

@ExperimentalCoroutinesApi
class ChatDataSourceTest : ChatDataSourceTestUtil() {

    lateinit var chatDataSource: ChatDataSource

    private lateinit var mockedMimeTypeMap: MockedStatic<MimeTypeMap>

    @Mock
    private lateinit var file: File

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockedMimeTypeMap = Mockito.mockStatic(MimeTypeMap::class.java)
        val mimeTypeMap = Mockito.mock(MimeTypeMap::class.java)
        val testMineType = "png"
        Mockito
            .`when`(MimeTypeMap.getFileExtensionFromUrl(file.absolutePath))
            .thenReturn(testMineType)
        Mockito
            .`when`(MimeTypeMap.getSingleton())
            .thenReturn(mimeTypeMap)
        Mockito
            .`when`(MimeTypeMap.getSingleton().getMimeTypeFromExtension(testMineType))
            .thenReturn(testMineType)
    }

    @After
    fun teardown() {
        mockedMimeTypeMap.close()
    }

    /**
     * Test case: should be able to return true when executed.
     * @assertions:
     *       assertThat: result is equal to true.
     * */
    @Test
    fun shouldReturnTrueWhenUploadBulletinVideoIsExecuted() = runBlocking {
        chatDataSource = getTestScenarioSuccess()
        val testValue = String.empty()
        val result = chatDataSource.uploadBulletinVideo(
            actionType = testValue,
            collection = testValue,
            file = file,
            idToken = testValue,
            image = testValue,
            title = testValue,
            content = testValue
        )
        Truth
            .assertThat(result.asLoadResult().data)
            .isTrue()
    }

    /**
     * Test case: should not be able to return true when executed.
     * @assertions:
     *       assertThat: result is equal to null.
     * */
    @Test
    fun shouldReturnNullWhenUploadBulletinVideoIsExecuted() = runBlocking {
        chatDataSource = getTestScenarioFailed()
        val testValue = String.empty()
        val result = chatDataSource.uploadBulletinVideo(
            actionType = testValue,
            collection = testValue,
            file = file,
            idToken = testValue,
            image = testValue,
            title = testValue,
            content = testValue
        )
        Truth
            .assertThat(result.asLoadResult().data)
            .isNull()
    }

    /**
     * Test case: should be able to return true when executed.
     * @assertions:
     *       assertThat: result is equal to true.
     * */
    @Test
    fun shouldReturnTrueWhenUploadChatVideoIsExecuted() = runBlocking {
        chatDataSource = getTestScenarioSuccess()
        val testValue = String.empty()
        val result = chatDataSource.uploadChatVideo(
            actionType = testValue,
            collection = testValue,
            file = file,
            groupId = testValue,
            idToken = testValue,
            targetChatId = testValue,
            targetUserId = testValue
        )
        Truth
            .assertThat(result.asLoadResult().data)
            .isTrue()
    }

    /**
     * Test case: should not be able to return true when executed.
     * @assertions:
     *       assertThat: result is equal to null.
     * */
    @Test
    fun shouldReturnNullWhenUploadChatVideoIsExecuted() = runBlocking {
        chatDataSource = getTestScenarioFailed()
        val testValue = String.empty()
        val result = chatDataSource.uploadChatVideo(
            actionType = testValue,
            collection = testValue,
            file = file,
            groupId = testValue,
            idToken = testValue,
            targetChatId = testValue,
            targetUserId = testValue
        )
        Truth
            .assertThat(result.asLoadResult().data)
            .isNull()
    }
}