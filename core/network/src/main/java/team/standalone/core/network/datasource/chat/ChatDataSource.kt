package team.standalone.core.network.datasource.chat

import team.standalone.core.common.util.Result
import java.io.File

interface ChatDataSource {
    suspend fun uploadBulletinVideo(
        actionType: String,
        collection: String,
        file: File,
        idToken: String,
        image: String,
        title: String,
        content: String,
    ): Result<Boolean>

    suspend fun uploadChatVideo(
        actionType: String,
        collection: String,
        file: File,
        groupId: String,
        idToken: String,
        targetChatId: String,
        targetUserId: String
    ): Result<Boolean>
}