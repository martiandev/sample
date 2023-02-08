package team.standalone.core.common.mapper

interface EntityMapper<in Entity, out Domain> {

    fun mapToDomain(entity: Entity): Domain

    fun mapToDomain(entityList: List<Entity>): List<Domain>
}