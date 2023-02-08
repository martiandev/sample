package team.standalone.core.network.util.test

import team.standalone.core.network.model.ArtistRemote
import java.util.*

open class ArtistTestUtil {

    val expectedUid: String = "123abc"
    val expectedChatColor: String = "#FFFFFF"
    val expectedIcon: String = "data:/sample/jpg/base64.."
    val expectedNickName: String = "Admin"
    val expectedDate: Date = Date()

    fun getTestArtistRemote(): ArtistRemote {
        return ArtistRemote (
            uid = expectedUid,
            chatColor = expectedChatColor,
            icon = expectedIcon,
            nickname = expectedNickName,
            createdAt = expectedDate,
            updatedAt = expectedDate,
        )
    }
}