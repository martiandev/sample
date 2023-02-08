package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.repository.UserRepository
import javax.inject.Inject

class UpdateEmailUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        email: String
    ): Result<User> = taskWithResult(ioDispatcher) {
        when (val res = userRepository.updateEmail(email)) {
            is Result.Success -> {
                sendEmailVerificationUseCase()
                res
            }
            is Result.Error -> res
        }
    }
}