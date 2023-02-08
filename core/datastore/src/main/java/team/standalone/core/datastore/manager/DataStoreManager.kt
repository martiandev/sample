package team.standalone.core.datastore.manager

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import team.standalone.core.common.util.Lumberjack
import team.standalone.core.datastore.model.SettingsProto
import team.standalone.core.datastore.model.UserProto
import team.standalone.core.datastore.model.toProto
import team.standalone.core.datastore.proto.model.SettingsProtoFile
import team.standalone.core.datastore.proto.model.UserProtoFile
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    private val userProtoDataStore: DataStore<UserProtoFile>,
    private val settingsProtoDataStore: DataStore<SettingsProtoFile>
) {
    fun getUserProto(): Flow<UserProto> {
        return userProtoDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    Lumberjack.error("Error reading user: $exception")
                }
                emit(UserProtoFile.getDefaultInstance())
            }
            .map { protoFile ->
                protoFile.toProto()
            }
    }

    fun getSettingsProto(): Flow<SettingsProto> {
        return settingsProtoDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    Lumberjack.error("Error reading settings: $exception")
                }
                emit(SettingsProtoFile.getDefaultInstance())
            }
            .map { protoFile ->
                protoFile.toProto()
            }
    }

    suspend fun updateLanguage(language: String): SettingsProto {
        return settingsProtoDataStore.updateData { protoFile ->
            protoFile.toBuilder()
                .setChatLanguage(language.uppercase())
                .build()
        }.toProto()
    }
}