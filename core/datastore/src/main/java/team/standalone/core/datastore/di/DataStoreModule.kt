package team.standalone.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.datastore.proto.model.SettingsProtoFile
import team.standalone.core.datastore.proto.model.UserProtoFile
import team.standalone.core.datastore.serializer.SettingsProtoFileSerializer
import team.standalone.core.datastore.serializer.UserProtoFileSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {
    @Singleton
    @Provides
    fun providesSettingsProtoDataStore(
        @ApplicationContext appContext: Context,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        settingsProtoFileSerializer: SettingsProtoFileSerializer
    ): DataStore<SettingsProtoFile> {
        return DataStoreFactory.create(
            serializer = settingsProtoFileSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler {
                settingsProtoFileSerializer.defaultValue
            },
            migrations = listOf(),
            scope = CoroutineScope(ioDispatcher + SupervisorJob()),
            produceFile = {
                appContext.dataStoreFile("settings_proto_file.pb")
            }
        )
    }

    @Singleton
    @Provides
    fun providesUserProtoDataStore(
        @ApplicationContext appContext: Context,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        userProtoFileSerializer: UserProtoFileSerializer
    ): DataStore<UserProtoFile> {
        return DataStoreFactory.create(
            serializer = userProtoFileSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler {
                userProtoFileSerializer.defaultValue
            },
            migrations = listOf(),
            scope = CoroutineScope(ioDispatcher + SupervisorJob()),
            produceFile = {
                appContext.dataStoreFile("user_proto_file.pb")
            }
        )
    }
}
