package team.standalone.core.data.domain.repository

import kotlinx.coroutines.flow.Flow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.Result
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.UserParam

interface UserRepository {
    /**
     * Get user uid
     * @return uid
     * @exception
     * - AuthException.UnauthorizedException
     */
    fun getUserUid(): Result<String>

    /**
     * Get user email
     * @return email
     * @exception
     * - AuthException.UnauthorizedException
     * - UserException.EmailNotFoundException
     */
    fun getUserEmail(): Result<String>

    /**
     * Check if user email is verified
     * @return Result.Success if verified. Otherwise, Result.Error
     * @exception
     * - AuthException.UnauthorizedException
     * - AuthException.EmailNotVerifiedException
     */
    fun isEmailVerified(): Result<Unit>

    /**
     * Get user
     * @param shouldFetch - If true, fetch user from remote and then save it to database.
     * Otherwise, get user from database.
     * @return flow of user
     * @exception
     * - AuthException.UnauthorizedException
     * - UserException.UserNotFoundException
     */
    fun getUserAsFlow(shouldFetch: Boolean): Flow<LoadResult<User>>

    /**
     * Update user
     * @param param - contains attributes of user that needs to be updated
     * @return user
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun updateUser(param: UserParam): Result<User>

    /**
     * Update user photo
     * @param photo - base64 value of photo
     * @return user
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun updateUserPhoto(photo: String): Result<User>

    /**
     * Delete user photo
     * @return user
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun deleteUserPhoto(): Result<User>

    /**
     * Update email
     * @param newEmail - new email
     * @return user remote
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun updateEmail(newEmail: String): Result<User>

    /**
     * Update password
     * @param newPassword - new password
     * @return Result.Success if success. Otherwise, Result.Error
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun updatePassword(newPassword: String): Result<Unit>

    /**
     * Save new user
     * @return user
     * @exception
     * - AuthException.UnauthorizedException
     * - UserException.UserNotFoundException
     */
    suspend fun saveNewUser(param: UserParam): Result<User>

    /**
     * Send email verification
     * @return Result.Success if success. Otherwise, Result.Error
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun sendEmailVerification(verificationUrl: String): Result<Unit>

    /**
     * Disable user from firestore and then delete the account
     * @return Result.Success if success. Otherwise, Result.Error
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun deleteAccount(): Result<Unit>
}