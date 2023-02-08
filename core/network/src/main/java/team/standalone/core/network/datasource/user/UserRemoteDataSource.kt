package team.standalone.core.network.datasource.user

import team.standalone.core.common.util.Result
import team.standalone.core.network.model.UserRemote
import team.standalone.core.network.model.request.UserRequest

interface UserRemoteDataSource {
    /**
     * Get user uid
     * @return uid
     * @exception
     * - AuthException.UnauthorizedException
     */
    fun getUserUid(): Result<String>

    /**
     * Get user token
     * @return token
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun getToken():Result<String>

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
     * @return user remote
     * @exception
     * - AuthException.UnauthorizedException
     * - UserException.UserNotFoundException
     */
    suspend fun getUser(): Result<UserRemote>

    /**
     * Update user
     * @param request - contains attributes of user that needs to be updated
     * @return user remote
     * @exception
     * - AuthException.UnauthorizedException
     * - UserException.UserNotFoundException
     */
    suspend fun updateUser(request: UserRequest): Result<UserRemote>

    /**
     * Update user photo
     * @param photo - base64 value of photo
     * @return user remote
     * @exception
     * - AuthException.UnauthorizedException
     * - UserException.UserNotFoundException
     */
    suspend fun updateUserPhoto(photo: String): Result<UserRemote>

    /**
     * Delete user photo
     * @return user remote
     * @exception
     * - AuthException.UnauthorizedException
     * - UserException.UserNotFoundException
     */
    suspend fun deleteUserPhoto(): Result<UserRemote>

    /**
     * Update email
     * @param newEmail - new email
     * @return user remote
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun updateEmail(newEmail: String): Result<UserRemote>

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
     * @return user remote
     * @exception
     * - AuthException.UnauthorizedException
     * - UserException.UserNotFoundException
     */
    suspend fun saveNewUser(request: UserRequest): Result<UserRemote>

    /**
     * Send email verification
     * @return Result.Success if success. Otherwise, Result.Error
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun sendEmailVerification(verificationUrl: String): Result<Unit>

    /**
     * Disable user
     * @return Result.Success if success. Otherwise, Result.Error
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun disableUser(): Result<Unit>

    /**
     * Delete user account
     * @return Result.Success if success. Otherwise, Result.Error
     * @exception
     * - AuthException.UnauthorizedException
     */
    suspend fun deleteAccount(): Result<Unit>
}