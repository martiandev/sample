package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.User
import javax.inject.Inject

class ReSignInUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val signInUseCase: SignInUseCase,
) {
    suspend operator fun invoke(
        password: String
    ): Result<User> = taskWithResult(ioDispatcher) {
        signInUseCase(password)
    }
}