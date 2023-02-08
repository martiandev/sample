package team.standalone.core.data.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.networkBoundResult
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.data.mapper.UserEntityMapper
import team.standalone.core.data.data.mapper.UserParamMapper
import team.standalone.core.data.data.mapper.UserRemoteMapper
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.UserParam
import team.standalone.core.data.domain.repository.UserRepository
import team.standalone.core.database.datasource.user.UserLocalDataSource
import team.standalone.core.network.datasource.user.UserRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userEntityMapper: UserEntityMapper,
    private val userRemoteMapper: UserRemoteMapper,
    private val userParamMapper: UserParamMapper,
) : UserRepository {

    override fun getUserUid(): Result<String> {
        return userRemoteDataSource.getUserUid()
    }

    override fun getUserEmail(): Result<String> {
        return userRemoteDataSource.getUserEmail()
    }

    override fun isEmailVerified(): Result<Unit> {
        return userRemoteDataSource.isEmailVerified()
    }

    override fun getUserAsFlow(
        shouldFetch: Boolean
    ): Flow<LoadResult<User>> = networkBoundResult(
        query = {
            val uid = when (val res = userRemoteDataSource.getUserUid()) {
                is Result.Success -> res.data
                is Result.Error -> throw res.exception
            }
            userLocalDataSource.getUserAsFlow(uid)
        },
        shouldFetch = { entity ->
            shouldFetch || entity == null
        },
        fetch = {
            userRemoteDataSource.getUser()
        },
        onFetchSuccess = { remote ->
            val entity = userRemoteMapper.mapToEntity(remote)
            userLocalDataSource.saveUser(entity)
        },
        onFetchFailed = {
            // do nothing
        },
        entityToDomain = { entity ->
            entity?.let(userEntityMapper::mapToDomain)
        }
    )

    override suspend fun updateUser(
        param: UserParam
    ): Result<User> = taskWithResult(ioDispatcher) {
        val request = userParamMapper.map(param)
        val userRemote = when (val res = userRemoteDataSource.updateUser(request)) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val entity = userRemoteMapper.mapToEntity(userRemote)
        userLocalDataSource.saveUser(entity)
        Result.Success(userEntityMapper.mapToDomain(entity))
    }

    override suspend fun updateUserPhoto(
        photo: String
    ): Result<User> = taskWithResult(ioDispatcher) {
        val userRemote = when (val res = userRemoteDataSource.updateUserPhoto(photo)) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val entity = userRemoteMapper.mapToEntity(userRemote)
        userLocalDataSource.saveUser(entity)
        Result.Success(userEntityMapper.mapToDomain(entity))
    }

    override suspend fun deleteUserPhoto(): Result<User> = taskWithResult(ioDispatcher) {
        val userRemote = when (val res = userRemoteDataSource.deleteUserPhoto()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val entity = userRemoteMapper.mapToEntity(userRemote)
        userLocalDataSource.saveUser(entity)
        Result.Success(userEntityMapper.mapToDomain(entity))
    }

    override suspend fun updateEmail(
        newEmail: String
    ): Result<User> = taskWithResult(ioDispatcher) {
        val userRemote = when(val res = userRemoteDataSource.updateEmail(newEmail)) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val entity = userRemoteMapper.mapToEntity(userRemote)
        userLocalDataSource.saveUser(entity)
        Result.Success(userEntityMapper.mapToDomain(entity))
    }

    override suspend fun updatePassword(
        newPassword: String
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        userRemoteDataSource.updatePassword(newPassword)
    }

    override suspend fun saveNewUser(
        param: UserParam
    ): Result<User> = taskWithResult(ioDispatcher) {
        val request = userParamMapper.map(param)
        val user = when (val res = userRemoteDataSource.saveNewUser(request)) {
            is Result.Success -> {
                val entity = userRemoteMapper.mapToEntity(res.data)
                userLocalDataSource.saveUser(entity)
                userEntityMapper.mapToDomain(entity)
            }
            is Result.Error -> return@taskWithResult res
        }
        Result.Success(user)
    }

    override suspend fun sendEmailVerification(
        verificationUrl: String
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        userRemoteDataSource.sendEmailVerification(verificationUrl)
    }

    override suspend fun deleteAccount(): Result<Unit> = taskWithResult(ioDispatcher) {
        when (val res = userRemoteDataSource.disableUser()) {
            is Result.Success -> userRemoteDataSource.deleteAccount()
            is Result.Error -> return@taskWithResult res
        }
    }
}