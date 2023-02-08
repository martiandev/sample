package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Result<Unit> = taskWithResult(ioDispatcher) {
        authRepository.signOut()
    }
}