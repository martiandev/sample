package team.standalone.core.data.domain.usecase

import team.standalone.core.common.util.Result
import team.standalone.core.data.domain.repository.UserRepository
import javax.inject.Inject

class GetUserEmailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Result<String> = userRepository.getUserEmail()
}