package team.standalone.core.data.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.data.mapper.SettingsProtoMapper
import team.standalone.core.data.domain.model.Settings
import team.standalone.core.data.domain.repository.SettingsRepository
import team.standalone.core.datastore.manager.DataStoreManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SettingsRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val dataStoreManager: DataStoreManager,
    private val settingsProtoMapper: SettingsProtoMapper,
) : SettingsRepository {

    override fun getSettingsAsFlow(): Flow<Settings> {
        return dataStoreManager.getSettingsProto()
            .map(settingsProtoMapper::mapToDomain)
    }

    override suspend fun updateLanguage(
        language: Settings.Language
    ): Result<Settings> = taskWithResult(ioDispatcher) {
        val settings = dataStoreManager.updateLanguage(language.name)
            .let(settingsProtoMapper::mapToDomain)
        Result.Success(settings)
    }
}