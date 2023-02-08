package team.standalone.core.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import team.standalone.core.database.room.model.entity.ArtistEntity
import team.standalone.core.database.room.util.BaseRoomDao

@Dao
abstract class ArtistDao : BaseRoomDao<ArtistEntity>() {
    @Query(
        """
        SELECT * FROM artist 
        WHERE uid=:uid
    """
    )
    abstract suspend fun get(uid: String): ArtistEntity?

    @Query(
        """
        SELECT * FROM artist 
        WHERE uid=:uid
    """
    )
    abstract fun getAsFlow(uid: String): Flow<ArtistEntity?>

    @Query(
        """
        DELETE FROM artist
        WHERE uid=:uid
    """
    )
    abstract suspend fun delete(uid: String): Int

    @Query(
        """
        DELETE FROM artist
    """
    )
    abstract suspend fun deleteAll(): Int
}