package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import team.standalone.core.common.util.LoadResult
import team.standalone.core.data.domain.model.Settings
import team.standalone.core.data.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<LoadResult<Settings>> {
        return settingsRepository.getSettingsAsFlow()
            .mapLatest {
                LoadResult.Success(it)
            }
    }
}