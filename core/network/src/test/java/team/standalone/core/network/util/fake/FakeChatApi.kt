package team.standalone.core.network.util.fake

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import team.standalone.core.network.model.response.FileApiResponse
import team.standalone.core.network.retrofit.api.ChatApi
import javax.inject.Inject

class FakeChatApi @Inject constructor(
    private val response: Response<FileApiResponse>
) : ChatApi {

    override suspend fun postBulletinVideo(
        actionType: RequestBody,
        collection: RequestBody,
        file: MultipartBody.Part,
        idToken: RequestBody,
        imageUrl: RequestBody,
        title: RequestBody,
        value: RequestBody
    ): Response<FileApiResponse> {
        return response
    }

    override suspend fun postChatVideo(
        actionType: RequestBody,
        collection: RequestBody,
        file: MultipartBody.Part,
        groupId: RequestBody,
        idToken: RequestBody,
        targetChatId: RequestBody,
        targetUserId: RequestBody
    ): Response<FileApiResponse> {
        return response
    }
}