package team.standalone.core.data.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.standalone.core.data.data.repository.*
import team.standalone.core.data.data.repository.ArtistRepositoryImpl
import team.standalone.core.data.data.repository.AuthRepositoryImpl
import team.standalone.core.data.data.repository.ChatRepositoryImpl
import team.standalone.core.data.data.repository.SettingsRepositoryImpl
import team.standalone.core.data.data.repository.UserRepositoryImpl
import team.standalone.core.data.domain.repository.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun bindsChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    @Singleton
    @Binds
    abstract fun bindsSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository

    @Singleton
    @Binds
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindArtistRepository(artistRepositoryImpl: ArtistRepositoryImpl): ArtistRepository

    @Singleton
    @Binds
    abstract fun bindsSubscriptionRepository(subscriptionRepository: SubscriptionRepositoryImpl): SubscriptionRepository
}