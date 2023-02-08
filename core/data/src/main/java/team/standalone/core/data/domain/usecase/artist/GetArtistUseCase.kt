package team.standalone.core.data.domain.usecase.artist

import kotlinx.coroutines.flow.Flow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.data.domain.model.Artist
import team.standalone.core.data.domain.repository.ArtistRepository
import javax.inject.Inject

class GetArtistUseCase @Inject constructor(
    private val artistRepository: ArtistRepository
) {
     operator fun invoke(shouldFetch:Boolean): Flow<LoadResult<Artist>> {
        return artistRepository.getArtistAsFlow(shouldFetch)
     }
}