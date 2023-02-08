package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.repository.ArtistRepository
import team.standalone.core.data.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserPhotoUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
    private val artistRepository: ArtistRepository,
) {
    suspend operator fun invoke(): Result<User> = taskWithResult(ioDispatcher) {
        // if user is also an artist, delete artist's photo
        when (artistRepository.getArtist()) {
            is Result.Success -> artistRepository.deleteArtistPhoto()
            is Result.Error -> Unit
        }

        userRepository.deleteUserPhoto()
    }
}