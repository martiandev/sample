package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.UserParam
import team.standalone.core.data.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        param: UserParam
    ): Result<User> = taskWithResult(ioDispatcher) {
        userRepository.updateUser(param)
    }
}