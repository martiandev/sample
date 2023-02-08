package team.standalone.core.datastore.model

import team.standalone.core.datastore.proto.model.SettingsProtoFile

data class SettingsProto(
    val chatLanguage: String
)

internal fun SettingsProtoFile.toProto(): SettingsProto {
    return SettingsProto(
        chatLanguage = chatLanguage
    )
}
