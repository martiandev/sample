package team.standalone.core.database.datasource.artist

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import team.standalone.core.common.exception.ArtistException
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Lumberjack
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.database.room.dao.ArtistDao
import team.standalone.core.database.room.model.entity.ArtistEntity
import javax.inject.Inject

internal class ArtistLocalDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val artistDao: ArtistDao,
) : ArtistLocalDataSource {

    override fun getArtistAsFlow(
        uid: String
    ): Flow<ArtistEntity?> {
        return artistDao.getAsFlow(uid)
            .distinctUntilChanged()
    }

    override suspend fun getArtist(
        uid: String
    ): Result<ArtistEntity> = taskWithResult(ioDispatcher) {
        artistDao.get(uid)?.let {
            Result.Success(it)
        } ?: throw ArtistException.ArtistNotFoundException
    }

    override suspend fun saveArtist(
        entity: ArtistEntity
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        try {
            artistDao.insertOrReplace(entity)
            Result.Success(Unit)

        } catch (e: Exception) {
            Lumberjack.error(e)
            Result.Error(e)
        }
    }

}