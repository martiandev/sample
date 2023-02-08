package team.standalone.core.data.domain.repository

import kotlinx.coroutines.flow.Flow
import team.standalone.core.common.util.Result
import team.standalone.core.data.domain.model.Settings

interface SettingsRepository {
    /**
     * Get settings
     * @return flow of settings
     */
    fun getSettingsAsFlow(): Flow<Settings>

    /**
     * Update language
     * @param language
     * @return settings
     */
    suspend fun updateLanguage(language: Settings.Language): Result<Settings>
}