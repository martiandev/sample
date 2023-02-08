package team.standalone.core.network.util.test

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import okhttp3.internal.http.HTTP_INTERNAL_SERVER_ERROR
import okhttp3.internal.http.HTTP_OK
import retrofit2.Response
import team.standalone.core.common.extension.empty
import team.standalone.core.network.datasource.chat.ChatDataSource
import team.standalone.core.network.datasource.chat.ChatDataSourceImpl
import team.standalone.core.network.model.response.FileApiResponse
import team.standalone.core.network.util.fake.FakeChatApi

@ExperimentalCoroutinesApi
open class ChatDataSourceTestUtil {

    private val expectedSuccess: Response<FileApiResponse> =
        Response.success(FileApiResponse(
            status = HTTP_OK,
            fileId = String.empty()
        ))

    private var expectedFailed: Response<FileApiResponse> =
        Response.success(FileApiResponse(
            status = HTTP_INTERNAL_SERVER_ERROR,
            fileId = String.empty()
        ))

    fun getTestScenarioSuccess() : ChatDataSource = ChatDataSourceImpl(
        UnconfinedTestDispatcher(),
        FakeChatApi(expectedSuccess)
    )

    fun getTestScenarioFailed() : ChatDataSource = ChatDataSourceImpl(
        UnconfinedTestDispatcher(),
        FakeChatApi(expectedFailed)
    )
}