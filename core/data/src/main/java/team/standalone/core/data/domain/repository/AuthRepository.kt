package team.standalone.core.data.domain.repository

import team.standalone.core.common.util.Result
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.SignInParam
import team.standalone.core.data.domain.model.param.SignUpParam

interface AuthRepository {
    /**
     * Sign in and also checks if email is verified
     * @param param - email and password
     * @return user
     * @exception
     * - AuthException.UnauthorizedException
     * - AuthException.EmailNotVerifiedException
     * - UserException.UserNotFoundException
     */
    suspend fun signIn(param: SignInParam): Result<User>

    /**
     * Sign in and also checks if email is verified
     * @param password - password
     * @return user
     * @exception
     * - AuthException.UnauthorizedException
     * - AuthException.EmailNotVerifiedException
     * - UserException.UserNotFoundException
     */
    suspend fun signIn(password: String): Result<User>

    /**
     * Sign up
     * @param param - email and password
     * @return Result.Success if success. Otherwise, Result.Error
     */
    suspend fun signUp(param: SignUpParam): Result<Unit>

    /**
     * Check if user is signed-in
     * @return user
     * @exception
     * - AuthException.UnauthorizedException
     * - AuthException.EmailNotVerifiedException
     * - UserException.UserNotFoundException
     */
    suspend fun checkAuthenticatedUser(): Result<User>

    /**
     * Reset password
     * @return Result.Success if success. Otherwise, Result.Error
     */
    suspend fun resetPassword(email: String): Result<Unit>

    /**
     * Sign out
     * @return Result.Success if success. Otherwise, Result.Error
     */
    suspend fun signOut(): Result<Unit>
}