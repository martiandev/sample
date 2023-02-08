package team.standalone.core.data.data.mapper

import kotlinx.datetime.Instant
import team.standalone.core.common.mapper.RemoteMapper
import team.standalone.core.database.room.model.entity.ArtistEntity
import team.standalone.core.network.model.ArtistRemote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ArtistRemoteMapper @Inject constructor() : RemoteMapper<ArtistRemote,ArtistEntity>{

    override fun mapToEntity(remote: ArtistRemote): ArtistEntity {
        return ArtistEntity(
            uid = remote.uid.orEmpty(),
            chatColor = remote.chatColor.orEmpty(),
            nickname = remote.nickname.orEmpty(),
            icon = remote.icon.orEmpty(),
            createdAt = remote.createdAt?.time?.let(Instant::fromEpochMilliseconds),
            updatedAt = remote.updatedAt?.time?.let(Instant::fromEpochMilliseconds)
        )
    }

    override fun mapToEntity(remoteList: List<ArtistRemote>): List<ArtistEntity> {
        return remoteList.map { mapToEntity(it) }
    }

}