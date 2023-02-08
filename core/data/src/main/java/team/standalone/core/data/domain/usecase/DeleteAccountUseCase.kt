package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.param.SignInParam
import team.standalone.core.data.domain.repository.UserRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val signInUseCase: SignInUseCase,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        val param = SignInParam(
            email = email,
            password = password
        )
        when (val res = signInUseCase(param)) {
            is Result.Success -> userRepository.deleteAccount()
            is Result.Error -> res
        }
    }

    suspend operator fun invoke(
        password: String
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        when (val res = signInUseCase(password)) {
            is Result.Success -> userRepository.deleteAccount()
            is Result.Error -> res
        }
    }
}