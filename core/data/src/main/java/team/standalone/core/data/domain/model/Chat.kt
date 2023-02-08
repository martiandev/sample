package team.standalone.core.data.domain.model

data class Chat(
    val actionType: String = "",
    val collection: String = "",
    val groupId: String = "",
    val targetChatId: String = "",
    val targetUserId: String = ""
)