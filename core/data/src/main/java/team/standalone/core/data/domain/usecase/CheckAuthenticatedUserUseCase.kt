package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.repository.AuthRepository
import javax.inject.Inject

class CheckAuthenticatedUserUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Result<User> = taskWithResult(ioDispatcher) {
        authRepository.checkAuthenticatedUser()
    }
}