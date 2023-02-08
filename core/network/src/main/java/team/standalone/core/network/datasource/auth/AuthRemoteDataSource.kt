package team.standalone.core.network.datasource.auth

import team.standalone.core.common.util.Result
import team.standalone.core.network.model.UserRemote
import team.standalone.core.network.model.request.SignInRequest
import team.standalone.core.network.model.request.SignUpRequest

interface AuthRemoteDataSource {
    /**
     * Sign in and also checks if email is verified
     * @param request - email and password
     * @return user remote
     * @exception
     * - AuthException.UnauthorizedException
     * - AuthException.EmailNotVerifiedException
     * - UserException.UserNotFoundException
     */
    suspend fun signIn(request: SignInRequest): Result<UserRemote>

    /**
     * Sign up
     * @param request - email and password
     * @return Result.Success if success. Otherwise, Result.Error
     */
    suspend fun signUp(request: SignUpRequest): Result<Unit>

    /**
     * Sign out
     * @return Result.Success if success. Otherwise, Result.Error
     */
    suspend fun signOut(): Result<Unit>

    /**
     * Check if user is signed-in
     * @return user remote
     * @exception
     * - AuthException.UnauthorizedException
     * - AuthException.EmailNotVerifiedException
     * - UserException.UserNotFoundException
     */
    suspend fun checkAuthenticatedUser(): Result<UserRemote>

    /**
     * Reset password
     * @return Result.Success if success. Otherwise, Result.Error
     */
    suspend fun resetPassword(email: String): Result<Unit>
}