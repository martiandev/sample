package team.standalone.core.common.mapper

interface RemoteMapper<in Remote, out Entity> {

    fun mapToEntity(remote: Remote): Entity

    fun mapToEntity(remoteList: List<Remote>): List<Entity>
}