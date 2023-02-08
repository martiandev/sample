package team.standalone.core.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import team.standalone.core.database.room.converter.InstantConverter
import team.standalone.core.database.room.dao.ArtistDao
import team.standalone.core.database.room.dao.UserDao
import team.standalone.core.database.room.model.entity.ArtistEntity
import team.standalone.core.database.room.model.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ArtistEntity::class
    ],
    version = 5,
    exportSchema = true
)
@TypeConverters(
    InstantConverter::class,
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun artistDao(): ArtistDao
}