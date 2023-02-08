package team.standalone.core.data.util.fake

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.exception.UserException
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.network.datasource.auth.AuthRemoteDataSource
import team.standalone.core.network.model.UserRemote
import team.standalone.core.network.model.request.SignInRequest
import team.standalone.core.network.model.request.SignUpRequest
import team.standalone.core.network.util.FakeUser
import javax.inject.Inject

class FakeAuthRemoteDataSource @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userRemoteDataSource: FakeUserRemoteDataSource
) : AuthRemoteDataSource {

    private var signedInUser: FakeUser? = null

    fun setSignedInUser(fakeUser: FakeUser?) {
        this.signedInUser = fakeUser
    }

    override suspend fun signIn(
        request: SignInRequest
    ): Result<UserRemote> =
        taskWithResult(ioDispatcher) {
            try {
                signedInUser?.let {
                    if (signedInUser?.email == request.email && signedInUser?.password != request.password) {
                        throw AuthException.InvalidCredentialsException
                    } else if (signedInUser?.email != request.email && signedInUser?.password != request.password) {
                        throw AuthException.InvalidUserException
                    } else {
                        signedInUser?.let { userRemoteDataSource.setSignedInUser(fakeUser = it) }
                        when (val res = userRemoteDataSource.isEmailVerified()) {
                            is Result.Success -> userRemoteDataSource.getUser()
                            is Result.Error -> return@taskWithResult res
                        }
                    }
                } ?: throw AuthException.UnauthorizedException
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun signUp(
        request: SignUpRequest
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        try {
            if (request.email.isEmpty() && request.password.isEmpty()) {
                throw AuthException.UnauthorizedException
            } else if (request.email == signedInUser?.email) {
                throw AuthException.EmailExistedException
            } else {
                userRemoteDataSource.setSignedInUser(
                    fakeUser = FakeUser(
                        uid = "",
                        email = request.email,
                        password = request.password,
                        isEmailVerified = false
                    )
                )
                Result.Success(Unit)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun signOut(): Result<Unit> = taskWithResult(ioDispatcher) {
        signedInUser?.let {
            signedInUser = null
            Result.Success(Unit)
        } ?: Result.Error(UserException.UserNotFoundException)
    }

    override suspend fun checkAuthenticatedUser(): Result<UserRemote> =
        taskWithResult(ioDispatcher) {
            signedInUser?.let { userRemoteDataSource.setSignedInUser(fakeUser = it) }
            when (val res = userRemoteDataSource.isEmailVerified()) {
                is Result.Success -> userRemoteDataSource.getUser()
                is Result.Error -> return@taskWithResult res
            }
        }

    override suspend fun resetPassword(
        email: String
    ): Result<Unit> =
        taskWithResult(ioDispatcher) {
            signedInUser?.let { fake ->
                if (email == fake.email) {
                    fake.password = ""
                } else {
                    return@taskWithResult Result.Error(AuthException.UnauthorizedException)
                }
            }
            Result.Success(Unit)
        }
}