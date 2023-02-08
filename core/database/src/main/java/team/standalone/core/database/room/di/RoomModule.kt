package team.standalone.core.database.room.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import team.standalone.core.common.qualifier.AppId
import team.standalone.core.common.util.Lumberjack
import team.standalone.core.database.room.AppDatabase
import team.standalone.core.database.room.dao.ArtistDao
import team.standalone.core.database.room.dao.UserDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RoomModule {
    @Singleton
    @Provides
    fun providesAppDatabase(
        @ApplicationContext context: Context,
        @AppId appId: String
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "$appId.database")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Lumberjack.info("Database created...")
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    Lumberjack.info("Database opened...")
                }
            })
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesUserDao(
        appDatabase: AppDatabase
    ): UserDao = appDatabase.userDao()

    @Provides
    fun provideArtistDao(
        appDatabase: AppDatabase
    ): ArtistDao = appDatabase.artistDao()



}