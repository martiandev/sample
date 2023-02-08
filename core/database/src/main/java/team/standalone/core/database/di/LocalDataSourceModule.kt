package team.standalone.core.database.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.standalone.core.database.datasource.artist.ArtistLocalDataSource
import team.standalone.core.database.datasource.artist.ArtistLocalDataSourceImpl
import team.standalone.core.database.datasource.user.UserLocalDataSource
import team.standalone.core.database.datasource.user.UserLocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalDataSourceModule {
    @Singleton
    @Binds
    abstract fun bindsUserLocalDataSource(
        userLocalDataSourceImpl: UserLocalDataSourceImpl
    ): UserLocalDataSource

    @Singleton
    @Binds
    abstract fun bindsArtistLocalDataSource(
        artistLocalDataSourceImpl: ArtistLocalDataSourceImpl
    ): ArtistLocalDataSource

}