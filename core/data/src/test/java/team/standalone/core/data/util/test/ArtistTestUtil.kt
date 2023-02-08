package team.standalone.core.data.util.test

import kotlinx.datetime.Instant
import team.standalone.core.data.domain.model.Artist
import team.standalone.core.database.room.model.entity.ArtistEntity
import team.standalone.core.network.model.ArtistRemote
import java.util.*
import kotlin.collections.ArrayList

open class ArtistTestUtil {

    var expectedUid: String = "123abc"
    var expectedChatColor: String = "#FFFFFF"
    var expectedIcon: String = "data:/sample/jpg/base64.."
    var expectedNickName: String = "Admin"
    var expectedDate: Date = Date()

    fun getTestArtist(instant: Instant): Artist {
        return Artist(
            uid = expectedUid,
            chatColor = expectedChatColor,
            icon = expectedIcon,
            nickName = expectedNickName,
            createdAt = instant,
            updatedAt = instant,
        )
    }

    fun getTestArtistEntity(instant: Instant): ArtistEntity {
        return ArtistEntity(
            uid = expectedUid,
            chatColor = expectedChatColor,
            icon = expectedIcon,
            nickname = expectedNickName,
            createdAt = instant,
            updatedAt = instant,
        )
    }

    fun getTestArtistRemote(): ArtistRemote {
        return ArtistRemote(
            uid = expectedUid,
            chatColor = expectedChatColor,
            icon = expectedIcon,
            nickname = expectedNickName,
            createdAt = expectedDate,
            updatedAt = expectedDate,
        )
    }

    fun getTestArtistEntityList(instant: Instant): MutableList<ArtistEntity> {
        val list: MutableList<ArtistEntity> = ArrayList()
        list.add(getTestArtistEntity(instant))
        return list
    }

    fun getTestArtistRemoteList(): MutableList<ArtistRemote> {
        val list: MutableList<ArtistRemote> = ArrayList()
        list.add(getTestArtistRemote())
        return list
    }
}