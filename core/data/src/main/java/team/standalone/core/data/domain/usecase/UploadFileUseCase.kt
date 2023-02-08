package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.Bulletin
import team.standalone.core.data.domain.model.Chat
import team.standalone.core.data.domain.repository.ChatRepository
import java.io.File
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(
        chat: Chat,
        file: File,
        idToken: String,
    ): Result<Boolean> = taskWithResult(ioDispatcher) {
        chatRepository.uploadChatVideo(chat, file, idToken)
    }

    suspend operator fun invoke(
        bulletin: Bulletin,
        file: File,
        idToken: String,
    ): Result<Boolean> = taskWithResult(ioDispatcher) {
        chatRepository.uploadBulletinVideo(bulletin, file, idToken)
    }
}