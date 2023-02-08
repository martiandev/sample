package team.standalone.core.network.datasource.auth

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.network.datasource.user.UserRemoteDataSource
import team.standalone.core.network.model.UserRemote
import team.standalone.core.network.model.request.SignInRequest
import team.standalone.core.network.model.request.SignUpRequest
import java.util.*
import javax.inject.Inject

internal class AuthRemoteDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val firebaseAuth: FirebaseAuth,
    private val userRemoteDataSource: UserRemoteDataSource,
) : AuthRemoteDataSource {

    override suspend fun signIn(
        request: SignInRequest
    ): Result<UserRemote> = taskWithResult(ioDispatcher) {
        try {
            firebaseAuth.signInWithEmailAndPassword(request.email, request.password).await()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw AuthException.InvalidCredentialsException
        } catch (e: FirebaseAuthInvalidUserException) {
            throw AuthException.InvalidUserException
        } catch (e: FirebaseNetworkException) {
            throw AuthException.NetworkException
        } catch (e: Exception) {
            throw AuthException.UnauthorizedException
        }

        when (val res = userRemoteDataSource.isEmailVerified()) {
            is Result.Success -> userRemoteDataSource.getUser()
            is Result.Error -> return@taskWithResult res
        }
    }

    override suspend fun signUp(
        request: SignUpRequest,
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(request.email, request.password).await()
            Result.Success(Unit)
        } catch (e: FirebaseAuthUserCollisionException) {
            throw AuthException.EmailExistedException
        } catch (e: FirebaseNetworkException) {
            throw AuthException.NetworkException
        } catch (e: Exception) {
            throw AuthException.UnauthorizedException
        }
    }

    override suspend fun signOut(): Result<Unit> = taskWithResult(ioDispatcher) {
        firebaseAuth.signOut()
        Result.Success(Unit)
    }

    override suspend fun checkAuthenticatedUser(): Result<UserRemote> =
        taskWithResult(ioDispatcher) {
            when (val res = userRemoteDataSource.isEmailVerified()) {
                is Result.Success -> userRemoteDataSource.getUser()
                is Result.Error -> return@taskWithResult res
            }
        }

    override suspend fun resetPassword(
        email: String
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        try {
            firebaseAuth.setLanguageCode(Locale.getDefault().language)
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.Success(Unit)
        } catch (e: FirebaseNetworkException) {
            throw AuthException.NetworkException
        } catch (e: Exception) {
            throw AuthException.UnauthorizedException
        }
    }
}