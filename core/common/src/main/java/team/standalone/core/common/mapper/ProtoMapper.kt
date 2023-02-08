package team.standalone.core.common.mapper

interface ProtoMapper<in Proto, out Domain> {
    fun mapToDomain(proto: Proto): Domain
}