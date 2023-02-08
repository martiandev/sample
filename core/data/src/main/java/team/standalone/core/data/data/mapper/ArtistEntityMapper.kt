package team.standalone.core.data.data.mapper

import team.standalone.core.common.mapper.EntityMapper
import team.standalone.core.data.domain.model.*
import team.standalone.core.database.room.model.entity.ArtistEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ArtistEntityMapper @Inject constructor() : EntityMapper<ArtistEntity, Artist> {

    override fun mapToDomain(entity: ArtistEntity): Artist {
        return Artist(
            uid = entity.uid,
            chatColor = entity.chatColor,
            icon = entity.icon,
            nickName = entity.nickname,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    override fun mapToDomain(entityList: List<ArtistEntity>): List<Artist> {
        return entityList.map { mapToDomain(it) }
    }
}