package team.standalone.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.standalone.core.network.datasource.artist.ArtistRemoteDataSource
import team.standalone.core.network.datasource.artist.ArtistRemoteDataSourceImpl
import team.standalone.core.network.datasource.auth.AuthRemoteDataSource
import team.standalone.core.network.datasource.auth.AuthRemoteDataSourceImpl
import team.standalone.core.network.datasource.chat.ChatDataSource
import team.standalone.core.network.datasource.chat.ChatDataSourceImpl
import team.standalone.core.network.datasource.subscription.SubscriptionRemoteDataSource
import team.standalone.core.network.datasource.subscription.SubscriptionRemoteDataSourceImpl
import team.standalone.core.network.datasource.user.UserRemoteDataSource
import team.standalone.core.network.datasource.user.UserRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteDataSourceModule {
    @Singleton
    @Binds
    abstract fun bindsAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindsChatDataSource(
        chatDataSourceImpl: ChatDataSourceImpl
    ): ChatDataSource

    @Singleton
    @Binds
    abstract fun bindsUserRemoteDataSource(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindArtistRemoteDataSource(
        artistRemoteDataSourceImpl: ArtistRemoteDataSourceImpl
    ): ArtistRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindsSubscriptionRemoteDataSource(
        subscriptionRemoteDataSourceImpl: SubscriptionRemoteDataSourceImpl
    ): SubscriptionRemoteDataSource
}