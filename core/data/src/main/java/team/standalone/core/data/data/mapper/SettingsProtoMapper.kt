package team.standalone.core.data.data.mapper

import team.standalone.core.common.mapper.ProtoMapper
import team.standalone.core.data.domain.model.Settings
import team.standalone.core.datastore.model.SettingsProto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SettingsProtoMapper @Inject constructor() : ProtoMapper<SettingsProto, Settings> {

    override fun mapToDomain(proto: SettingsProto): Settings {
        return Settings(
            language = try {
                Settings.Language.valueOf(proto.chatLanguage)
            } catch (e: Exception) {
                Settings.Language.DEFAULT
            }
        )
    }
}