package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.Settings
import team.standalone.core.data.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(
        language: Settings.Language
    ): Result<Settings> = taskWithResult(ioDispatcher) {
        settingsRepository.updateLanguage(language)
    }
}