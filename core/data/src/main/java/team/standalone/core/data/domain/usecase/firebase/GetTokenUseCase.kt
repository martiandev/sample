package team.standalone.core.data.domain.usecase.firebase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.network.datasource.user.UserRemoteDataSource
import javax.inject.Inject

class GetTokenUseCase@Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userRemoteDataSource: UserRemoteDataSource
 ) {

    suspend operator fun invoke(): Result<String> = taskWithResult(ioDispatcher) {
        userRemoteDataSource.getToken()
    }
}