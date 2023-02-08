package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.EmailVerification
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.repository.UserRepository
import javax.inject.Inject

class SendEmailVerificationUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @EmailVerification private val emailVerification: String,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> = taskWithResult(ioDispatcher) {
        userRepository.sendEmailVerification(emailVerification)
    }
}