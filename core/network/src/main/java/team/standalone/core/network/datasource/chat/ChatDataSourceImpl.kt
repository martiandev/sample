package team.standalone.core.network.datasource.chat

import android.webkit.MimeTypeMap
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.http.HTTP_OK
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.UriKeys.FILE
import team.standalone.core.common.util.UriKeys.VIDEO
import team.standalone.core.common.util.UriKeys.VIDEO_DEFAULT_TYPE
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.network.retrofit.api.ChatApi
import team.standalone.core.network.retrofit.util.ContentTypeKeys.MULTIPART_FORM_DATA
import java.io.File
import java.util.*
import javax.inject.Inject

class ChatDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val chatApi: ChatApi
) : ChatDataSource {

    /**
     * Upload video file from bulletin board to API.
     * */
    override suspend fun uploadBulletinVideo(
        actionType: String,
        collection: String,
        file: File,
        idToken: String,
        image: String,
        title: String,
        content: String,
    ): Result<Boolean> = taskWithResult(ioDispatcher) {
        try {
            val result = chatApi.postBulletinVideo(
                actionType.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull()),
                collection.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull()),
                fileToMultipartBody(file),
                idToken.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull()),
                image.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull()),
                title.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull()),
                content.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull())
            )
            when(result.body()?.status == HTTP_OK) {
                true -> Result.Success(data = true)
                else -> throw Exception()
            }
        } catch (error: Exception) {
            Result.Error(error)
        }
    }

    /**
     * Upload video file from chat to API.
     * */
    override suspend fun uploadChatVideo(
        actionType: String,
        collection: String,
        file: File,
        groupId: String,
        idToken: String,
        targetChatId: String,
        targetUserId: String
    ): Result<Boolean> = taskWithResult(ioDispatcher) {
        try {
            val result = chatApi.postChatVideo(
                actionType.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull()),
                collection.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull()),
                fileToMultipartBody(file),
                groupId.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull()),
                idToken.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull()),
                targetChatId.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull()),
                targetUserId.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull())
            )
            when(result.body()?.status == HTTP_OK) {
                true -> Result.Success(data = true)
                else -> throw Exception()
            }
        } catch (error: Exception) {
            Result.Error(error)
        }
    }

    /**
     * Parse file to MultipartBody.Part.
     * */
    fun fileToMultipartBody(file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            FILE,
            file.name,
            file.asRequestBody(
                getMimeType(file).toMediaTypeOrNull()
            )
        )
    }

    /**
     * Gets the mime type of a file.
     * */
    fun getMimeType(file: File): String {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(file.absolutePath)
        return if (fileExtension == "") {
            val characters = file.name.lastIndexOf('.')
            when (characters >= 0) {
                true -> {
                    val startIndex = characters + 1 // Get index of file extension after name index.
                    "$VIDEO/${file.name.substring(startIndex)}"
                }
                else -> "$VIDEO/$VIDEO_DEFAULT_TYPE"
            }
        } else {
            MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(fileExtension.lowercase(Locale.JAPAN)) as String
        }
    }
}