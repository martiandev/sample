package team.standalone.core.network.datasource.artist

import team.standalone.core.common.util.Result
import team.standalone.core.network.model.ArtistRemote

interface ArtistRemoteDataSource {
    /**
     * Retrieve Artist information
     * @return Result.Success if success. Otherwise, Result.Error
     * @exception
     * - ArtistException.ArtistNotFoundException
     */
    suspend fun getArtist(): Result<ArtistRemote>

    /**
     * Update artist photo
     * @param photo - base64 value of photo
     * @return artist remote
     * @exception
     * - AuthException.UnauthorizedException
     * - ArtistException.ArtistNotFoundException
     */
    suspend fun updateArtistPhoto(photo: String): Result<ArtistRemote>

    /**
     * Delete artist photo
     * @return artist remote
     * @exception
     * - AuthException.UnauthorizedException
     * - ArtistException.ArtistNotFoundException
     */
    suspend fun deleteArtistPhoto(): Result<ArtistRemote>
}