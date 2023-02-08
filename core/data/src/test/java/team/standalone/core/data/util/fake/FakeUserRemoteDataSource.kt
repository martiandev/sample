package team.standalone.core.data.util.fake

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.exception.UserException
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.network.datasource.user.UserRemoteDataSource
import team.standalone.core.network.model.UserRemote
import team.standalone.core.network.model.request.UserRequest
import team.standalone.core.network.util.FakeUser

class FakeUserRemoteDataSource(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userRemoteList: List<UserRemote>
) : UserRemoteDataSource {

    private var signedInUser: FakeUser? = null

    fun setSignedInUser(fakeUser: FakeUser?) {
        this.signedInUser = fakeUser
    }

    private fun getSignedInUser(): Result<FakeUser> {
        return signedInUser?.let {
            Result.Success(it)
        } ?: Result.Error(AuthException.UnauthorizedException)
    }

    override fun getUserUid(): Result<String> {
        return try {
            val uid = when (val res = getSignedInUser()) {
                is Result.Success -> res.data.uid
                is Result.Error -> return res
            }
            Result.Success(uid)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun getUserEmail(): Result<String> {
        return try {
            val email = when (val res = getSignedInUser()) {
                is Result.Success -> res.data.email ?: throw UserException.EmailNotFoundException
                is Result.Error -> return res
            }
            Result.Success(email)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun isEmailVerified(): Result<Unit> {
        return try {
            val isEmailVerified = when (val res = getSignedInUser()) {
                is Result.Success -> res.data.isEmailVerified
                is Result.Error -> return res
            }
            if (isEmailVerified) {
                Result.Success(Unit)
            } else {
                throw AuthException.EmailNotVerifiedException
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getUser(): Result<UserRemote> = taskWithResult(ioDispatcher) {
        val uid = when (val res = getUserUid()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val userRemote =
            userRemoteList.find { it.uid == uid } ?: throw UserException.UserNotFoundException
        Result.Success(userRemote)
    }

    override suspend fun getToken(): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(request: UserRequest): Result<UserRemote> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserPhoto(photo: String): Result<UserRemote> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUserPhoto(): Result<UserRemote> {
        TODO("Not yet implemented")
    }

    override suspend fun updateEmail(newEmail: String): Result<UserRemote> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePassword(newPassword: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNewUser(request: UserRequest): Result<UserRemote> {
        TODO("Not yet implemented")
    }

    override suspend fun sendEmailVerification(
        verificationUrl: String
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        when (val res = getSignedInUser()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        Result.Success(Unit)
    }

    override suspend fun disableUser(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount(): Result<Unit> {
        TODO("Not yet implemented")
    }
}