package team.standalone.core.data.domain.repository

import kotlinx.coroutines.flow.Flow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.Result
import team.standalone.core.data.domain.model.Artist
import team.standalone.core.data.domain.model.User

interface ArtistRepository {
    /**
     * Get artist
     * @return artist
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun getArtist(): Result<Artist>

    /**
     * Get artist
     * @param shouldFetch - If true, fetch user from remote and then save it to database.
     * Otherwise, get user from database.
     * @return flow of artist
     * @exception
     * - AuthException.UnauthorizedException
     */
    fun getArtistAsFlow(shouldFetch: Boolean): Flow<LoadResult<Artist>>

    /**
     * Update artist photo
     * @param photo - base64 value of photo
     * @return artist
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun updateArtistPhoto(photo: String): Result<Artist>

    /**
     * Delete artist photo
     * @return artist
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun deleteArtistPhoto(): Result<Artist>
}