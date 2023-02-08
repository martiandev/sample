package team.standalone.core.datastore.model

import team.standalone.core.datastore.proto.model.UserProtoFile

data class UserProto(
    val uid: String,
    val email: String,
    val isEmailVerified: Boolean
)

internal fun UserProtoFile.toProto(): UserProto {
    return UserProto(
        uid = uid,
        email = email,
        isEmailVerified = isEmailVerified,
    )
}
