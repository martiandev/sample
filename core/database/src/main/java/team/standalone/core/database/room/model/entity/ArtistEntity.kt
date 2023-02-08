package team.standalone.core.database.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "artist")
data class ArtistEntity (
    @PrimaryKey @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "chat_color") val chatColor: String,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "created_at") val createdAt: Instant?,
    @ColumnInfo(name = "updated_at") val updatedAt: Instant?,
)