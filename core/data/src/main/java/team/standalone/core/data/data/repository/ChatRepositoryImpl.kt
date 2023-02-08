package team.standalone.core.data.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.Bulletin
import team.standalone.core.data.domain.model.Chat
import team.standalone.core.data.domain.repository.ChatRepository
import team.standalone.core.network.datasource.chat.ChatDataSource
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ChatRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val chatDataSource: ChatDataSource
) : ChatRepository {

    /**
     * Upload video file from bulletin board to API.
     * */
    override suspend fun uploadBulletinVideo(
        bulletin: Bulletin,
        file: File,
        idToken: String
    ): Result<Boolean> = taskWithResult(ioDispatcher) {
        chatDataSource.uploadBulletinVideo(
            bulletin.actionType,
            bulletin.collection,
            file,
            idToken,
            bulletin.image,
            bulletin.title,
            bulletin.content,
        )
    }

    /**
     * Upload video file from chat to API.
     * */
    override suspend fun uploadChatVideo(
        chat: Chat,
        file: File,
        idToken: String,
    ): Result<Boolean> = taskWithResult(ioDispatcher) {
        chatDataSource.uploadChatVideo(
            chat.actionType,
            chat.collection,
            file,
            chat.groupId,
            idToken,
            chat.targetChatId,
            chat.targetUserId
        )
    }
}