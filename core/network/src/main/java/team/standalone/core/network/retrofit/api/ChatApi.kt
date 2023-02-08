package team.standalone.core.network.retrofit.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import team.standalone.core.network.model.response.FileApiResponse

interface ChatApi {

    /**
     * Upload video file from bulletin board to API.
     * */
    @Multipart
    @POST("api/file/uploadVideo")
    suspend fun postBulletinVideo(
        @Part("actionType") actionType: RequestBody,
        @Part("collection") collection: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("idToken") idToken: RequestBody,
        @Part("imageUrl") imageUrl: RequestBody,
        @Part("title") title: RequestBody,
        @Part("value") value: RequestBody,
    ): Response<FileApiResponse>

    /**
     * Upload video file from chat to API.
     * */
    @Multipart
    @POST("api/file/uploadVideo")
    suspend fun postChatVideo(
        @Part("actionType") actionType: RequestBody,
        @Part("collection") collection: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("groupId") groupId: RequestBody,
        @Part("idToken") idToken: RequestBody,
        @Part("targetChatId") targetChatId: RequestBody,
        @Part("targetUserId") targetUserId: RequestBody,
    ): Response<FileApiResponse>
}