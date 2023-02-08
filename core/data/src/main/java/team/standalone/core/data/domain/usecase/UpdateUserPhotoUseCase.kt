package team.standalone.core.data.domain.usecase

import android.net.Uri
import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.repository.ArtistRepository
import team.standalone.core.data.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserPhotoUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val parseUriToBase64UseCase: ParseUriToBase64UseCase,
    private val userRepository: UserRepository,
    private val artistRepository: ArtistRepository,
) {
    suspend operator fun invoke(uri: Uri): Result<User> = taskWithResult(ioDispatcher) {
        val photo = when (val res = parseUriToBase64UseCase(uri)) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        // if user is also an artist, update artist's photo
        when (artistRepository.getArtist()) {
            is Result.Success -> artistRepository.updateArtistPhoto(photo)
            is Result.Error -> Unit
        }

        userRepository.updateUserPhoto(photo)
    }
}