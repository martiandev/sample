package team.standalone.core.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import team.standalone.core.database.room.model.entity.UserEntity
import team.standalone.core.database.room.util.BaseRoomDao

@Dao
abstract class UserDao : BaseRoomDao<UserEntity>() {
    @Query(
        """
        SELECT * FROM users 
        WHERE uid=:uid
    """
    )
    abstract suspend fun get(uid: String): UserEntity?

    @Query(
        """
        SELECT * FROM users 
        WHERE uid=:uid
    """
    )
    abstract fun getAsFlow(uid: String): Flow<UserEntity?>

    @Query(
        """
        DELETE FROM users
        WHERE uid=:uid
    """
    )
    abstract suspend fun delete(uid: String): Int

    @Query(
        """
        DELETE FROM users
    """
    )
    abstract suspend fun deleteAll(): Int
}