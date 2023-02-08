package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.repository.AuthRepository
import team.standalone.core.data.domain.repository.UserRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        email: String
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        authRepository.resetPassword(email)
    }

    suspend operator fun invoke(): Result<Unit> = taskWithResult(ioDispatcher) {
        val email = when(val res = userRepository.getUserEmail()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        authRepository.resetPassword(email)
    }
}