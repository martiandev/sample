package team.standalone.core.data.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.data.mapper.UserEntityMapper
import team.standalone.core.data.data.mapper.UserRemoteMapper
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.SignInParam
import team.standalone.core.data.domain.model.param.SignUpParam
import team.standalone.core.data.domain.repository.AuthRepository
import team.standalone.core.database.datasource.user.UserLocalDataSource
import team.standalone.core.network.datasource.auth.AuthRemoteDataSource
import team.standalone.core.network.datasource.user.UserRemoteDataSource
import team.standalone.core.network.model.request.SignInRequest
import team.standalone.core.network.model.request.SignUpRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AuthRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteMapper: UserRemoteMapper,
    private val userEntityMapper: UserEntityMapper
) : AuthRepository {

    override suspend fun signIn(
        param: SignInParam
    ): Result<User> = taskWithResult(ioDispatcher) {
        val request = SignInRequest(
            email = param.email,
            password = param.password
        )
        val userRemote = when (val res = authRemoteDataSource.signIn(request)) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        val userEntity = userRemoteMapper.mapToEntity(userRemote)
        userLocalDataSource.saveUser(userEntity)
        Result.Success(userEntityMapper.mapToDomain(userEntity))
    }

    override suspend fun signIn(
        password: String
    ): Result<User> = taskWithResult(ioDispatcher) {
        val email = when (val res = userRemoteDataSource.getUserEmail()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        val param = SignInParam(
            email = email,
            password = password
        )
        signIn(param)
    }

    override suspend fun signUp(
        param: SignUpParam,
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        val request = SignUpRequest(
            email = param.email,
            password = param.password
        )
        authRemoteDataSource.signUp(request)
    }

    override suspend fun checkAuthenticatedUser(): Result<User> = taskWithResult(ioDispatcher) {
        val userRemote = when (val res = authRemoteDataSource.checkAuthenticatedUser()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val userEntity = userRemoteMapper.mapToEntity(userRemote)
        userLocalDataSource.saveUser(userEntity)
        Result.Success(userEntityMapper.mapToDomain(userEntity))
    }

    override suspend fun resetPassword(
        email: String
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        authRemoteDataSource.resetPassword(email)
    }

    override suspend fun signOut(): Result<Unit> = taskWithResult(ioDispatcher) {
        authRemoteDataSource.signOut()
    }
}