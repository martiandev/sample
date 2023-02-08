package team.standalone.core.data.domain.repository

import team.standalone.core.common.util.Result
import team.standalone.core.data.domain.model.Bulletin
import team.standalone.core.data.domain.model.Chat
import java.io.File

interface ChatRepository {
    suspend fun uploadBulletinVideo(
        bulletin: Bulletin,
        file: File,
        idToken: String
    ): Result<Boolean>

    suspend fun uploadChatVideo(
        chat: Chat,
        file: File,
        idToken: String
    ): Result<Boolean>
}