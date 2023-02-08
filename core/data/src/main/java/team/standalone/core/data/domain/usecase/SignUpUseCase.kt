package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.param.SignUpParam
import team.standalone.core.data.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        param: SignUpParam
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        when (val res = authRepository.signUp(param)) {
            is Result.Success -> sendEmailVerificationUseCase()
            is Result.Error -> res
        }
    }
}