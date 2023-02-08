package team.standalone.core.data.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.networkBoundResult
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.data.mapper.ArtistEntityMapper
import team.standalone.core.data.data.mapper.ArtistRemoteMapper
import team.standalone.core.data.domain.model.Artist
import team.standalone.core.data.domain.repository.ArtistRepository
import team.standalone.core.database.datasource.artist.ArtistLocalDataSource
import team.standalone.core.network.datasource.artist.ArtistRemoteDataSource
import team.standalone.core.network.datasource.user.UserRemoteDataSource
import team.standalone.core.network.util.ConnectionManager
import javax.inject.Inject

internal class ArtistRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val artistRemoteDataSource: ArtistRemoteDataSource,
    private val artistLocalDataSource: ArtistLocalDataSource,
    private val artistEntityMapper: ArtistEntityMapper,
    private val artistRemoteMapper: ArtistRemoteMapper,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val connectionManager: ConnectionManager
) : ArtistRepository {

    override suspend fun getArtist(): Result<Artist> = taskWithResult(ioDispatcher) {
        val artistRemote = when(val res = artistRemoteDataSource.getArtist()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val entity = artistRemoteMapper.mapToEntity(artistRemote)
        artistLocalDataSource.saveArtist(entity)
        Result.Success(artistEntityMapper.mapToDomain(entity))
    }

    override fun getArtistAsFlow(
        shouldFetch: Boolean
    ): Flow<LoadResult<Artist>> = networkBoundResult(
        query = {
            val uid = when (val res = userRemoteDataSource.getUserUid()) {
                is Result.Success -> res.data
                is Result.Error -> throw res.exception
            }
            artistLocalDataSource.getArtistAsFlow(uid)
        },
        shouldFetch = { entity ->
            shouldFetch || entity == null
        },
        fetch = {
            artistRemoteDataSource.getArtist()
        },
        onFetchSuccess = { remote ->
            val entity = artistRemoteMapper.mapToEntity(remote)
            artistLocalDataSource.saveArtist(entity)
        },
        onFetchFailed = {
            Log.i("ARTIST-409", "Fetch Failed")
        },
        entityToDomain = { entity ->
            entity?.let(artistEntityMapper::mapToDomain)
        }
    )

    override suspend fun updateArtistPhoto(
        photo: String
    ): Result<Artist> = taskWithResult(ioDispatcher) {
        if (!connectionManager.isNetworkConnected()) throw AuthException.NetworkException

        val artistRemote = when (val res = artistRemoteDataSource.updateArtistPhoto(photo)) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val entity = artistRemoteMapper.mapToEntity(artistRemote)
        artistLocalDataSource.saveArtist(entity)
        Result.Success(artistEntityMapper.mapToDomain(entity))
    }

    override suspend fun deleteArtistPhoto(): Result<Artist> = taskWithResult(ioDispatcher) {
        if (!connectionManager.isNetworkConnected()) throw AuthException.NetworkException

        val artistRemote = when (val res = artistRemoteDataSource.deleteArtistPhoto()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val entity = artistRemoteMapper.mapToEntity(artistRemote)
        artistLocalDataSource.saveArtist(entity)
        Result.Success(artistEntityMapper.mapToDomain(entity))
    }
}