package team.standalone.fumiya.ui.base

import android.net.Uri
import android.webkit.JavascriptInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.model.Bulletin
import team.standalone.core.data.domain.model.Chat
import team.standalone.core.data.domain.model.Settings
import team.standalone.core.data.domain.model.Settings.Language.*
import team.standalone.core.data.domain.usecase.*
import team.standalone.core.data.domain.usecase.firebase.GetTokenUseCase
import team.standalone.core.ui.viewmodel.BaseWebViewModel
import team.standalone.fumiya.ui.base.ChatUiEvent.*
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private var copyTextUseCase: CopyTextUseCase,
    getLocaleUseCase: GetLocaleUseCase,
    getSettingsUseCase: GetSettingsUseCase,
    getTokenUseCase: GetTokenUseCase,
    getUserUseCase: GetUserUseCase,
    private var parseUriToBase64UseCase: ParseUriToBase64UseCase,
    private var uploadFileUseCase: UploadFileUseCase,
) : BaseWebViewModel<ChatUiEvent>(
    getLocaleUseCase,
    getTokenUseCase,
    getUserUseCase
), ChatUiEventListener {

    private lateinit var chat: Chat
    private lateinit var bulletin: Bulletin
    private lateinit var bulletinVideoFile: File

    private val _imageResult = Channel<LoadResult<String>>()
    private val _chatUploadResult = Channel<LoadResult<Unit>>()

    val imageResult = _imageResult.receiveAsFlow()
    val chatUploadResult = _chatUploadResult.receiveAsFlow()

    /**
     * Get settings data.
     * */
    val uiSettingsState = getSettingsUseCase()

    /**
     * Format uri to base64 string.
     * */
    override fun formatPhoto(uri: Uri) = runUseCase {
        _imageResult.trySend(parseUriToBase64UseCase(uri).asLoadResult())
    }

    /**
     * Hold video file awaiting for bulletin board savings action.
     * */
    override fun formatVideo(file: File) = runUseCase {
        sendUiEvent(DataFileName(file.name))
        bulletinVideoFile = file
    }

    /**
     * Get language settings.
     * */
    override fun getLanguage(settings: Settings?): String {
        return when(settings?.language) {
            DEFAULT -> KEY_DEFAULT
            JAPANESE -> KEY_JAPANESE
            ENGLISH -> KEY_ENGLISH
            SIMPLIFIED_CHINESE -> KEY_SIMPLIFIED_CHINESE
            TRADITIONAL_CHINESE -> KEY_TRADITIONAL_CHINESE
            KOREAN -> KEY_KOREAN
            else -> KEY_DEFAULT
        }
    }

    /**
     * Handle events for uploading video files from chat.
     * */
    override fun uploadBulletinVideo(file: File) = runUseCase {
        uploadFileUseCase(bulletin, file, getIdToken())
    }

    /**
     * Handle events for uploading video files from chat.
     * */
    override fun uploadChatVideo(file: File) = runUseCase {
        _chatUploadResult.trySend(LoadResult.Loading())
        _chatUploadResult.trySend(
            when (val result = uploadFileUseCase(chat, file, getIdToken())) {
                is Result.Success -> LoadResult.Success()
                is Result.Error -> LoadResult.Error(Unit, result.exception)
            }
        )
    }

    /**
     * Javascript interface function for 'createBulletinCallback'.
     * */
    @JavascriptInterface
    override fun createBulletinCallback(data: Bulletin) {
        bulletin = Bulletin(
            actionType = BULLETIN,
            collection = data.collection,
            content = data.content,
            image = data.image,
            title = data.title,
            uid = data.uid
        )
        runUseCase {
            uploadFileUseCase(bulletin, bulletinVideoFile, getIdToken())
        }
    }

    /**
     * Javascript interface function for 'cameraCallback'.
     * */
    @JavascriptInterface
    override fun cameraCallback() {
        sendUiEvent(CameraJavascriptInterface)
    }

    /**
     * Javascript interface function for 'copyCallback'.
     * */
    @JavascriptInterface
    override fun copyCallback(text: String) {
        copyTextUseCase(text)
    }

    /**
     * Javascript interface function for 'galleryCallback'.
     * */
    @JavascriptInterface
    override fun galleryCallback(
        groupId: String,
        actionType: String,
        targetChatId: String,
        targetUserId: String
    ) {
        chat = Chat(
            actionType = actionType,
            collection = CHAT,
            groupId = groupId,
            targetChatId = targetChatId,
            targetUserId = targetUserId,
        )
        sendUiEvent(GalleryJavascriptInterface)
    }

    /**
     * Javascript interface function for 'videoCallback'.
     * */
    @JavascriptInterface
    override fun videoCallback() {
        sendUiEvent(VideoJavascriptInterface)
    }

    companion object {
        private const val BULLETIN = "bulletin"
        private const val CHAT = "chatItem"
        private const val KEY_DEFAULT = "none"
        private const val KEY_JAPANESE = "ja"
        private const val KEY_ENGLISH = "en"
        private const val KEY_SIMPLIFIED_CHINESE = "zh"
        private const val KEY_TRADITIONAL_CHINESE = "zh-TW"
        private const val KEY_KOREAN = "ko"
    }
}

interface ChatUiEventListener {
    fun formatPhoto(uri: Uri)
    fun formatVideo(file: File)
    fun getLanguage(settings: Settings?): String
    fun uploadBulletinVideo(file: File)
    fun uploadChatVideo(file: File)
    fun createBulletinCallback(data: Bulletin)
    fun cameraCallback()
    fun copyCallback(text: String)
    fun galleryCallback(
        groupId: String,
        actionType: String,
        targetChatId: String,
        targetUserId: String
    )
    fun videoCallback()
}

sealed class ChatUiEvent {
    object CameraJavascriptInterface : ChatUiEvent()
    object GalleryJavascriptInterface : ChatUiEvent()
    object VideoJavascriptInterface : ChatUiEvent()
    data class DataFileName(val name: String) : ChatUiEvent()
}
