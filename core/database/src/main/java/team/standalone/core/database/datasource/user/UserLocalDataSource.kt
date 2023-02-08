package team.standalone.core.database.datasource.user

import kotlinx.coroutines.flow.Flow
import team.standalone.core.common.util.Result
import team.standalone.core.database.room.model.entity.UserEntity

interface UserLocalDataSource {
    /**
     * Get user entity
     * @param uid - user uid
     * @return user entity
     * @exception
     * - UserException.UserNotFoundException
     */
    suspend fun getUser(uid: String): Result<UserEntity>

    /**
     * Get user entity
     * @param uid - user uid
     * @return flow of user entity
     */
    fun getUserAsFlow(uid: String): Flow<UserEntity?>

    /**
     * Save user entity
     * @return result
     */
    suspend fun saveUser(entity: UserEntity): Result<Unit>

    /**
     * Delete user
     * @param uid - user uid
     * @return Result.Success if success. Otherwise, Result.Error
     */
    suspend fun deleteUser(uid: String): Result<Unit>

    /**
     * Delete all user
     * @return Result.Success if success. Otherwise, Result.Error
     */
    suspend fun deleteAll(): Result<Unit>
}