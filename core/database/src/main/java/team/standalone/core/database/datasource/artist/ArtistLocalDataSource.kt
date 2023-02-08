package team.standalone.core.database.datasource.artist

import kotlinx.coroutines.flow.Flow
import team.standalone.core.common.util.Result
import team.standalone.core.database.room.model.entity.ArtistEntity

interface ArtistLocalDataSource {

    /**
     * Get artist entity as a flow
     * @param uid - user uid
     * @return flow of Artist entity
     */
    fun getArtistAsFlow(uid: String): Flow<ArtistEntity?>

    /**
     * Get artist entity
     * @param uid - user uid
     * @return Artist entity
     */
    suspend fun getArtist(
        uid: String
    ): Result<ArtistEntity>

    /**
     * Save artist entity
     * @return result
     */
    suspend fun saveArtist(
        entity: ArtistEntity
    ): Result<Unit>

}