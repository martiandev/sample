package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.flow.Flow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(shouldFetch: Boolean): Flow<LoadResult<User>> {
        return userRepository.getUserAsFlow(shouldFetch)
    }
}