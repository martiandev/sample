package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.SignInParam
import team.standalone.core.data.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        param: SignInParam
    ): Result<User> = taskWithResult(ioDispatcher) {
        authRepository.signIn(param)
    }

    suspend operator fun invoke(
        password: String
    ): Result<User> = taskWithResult(ioDispatcher) {
        authRepository.signIn(password)
    }
}