package team.standalone.core.network.util

data class FakeUser(
    val uid: String,
    val email: String?,
    var password: String,
    val isEmailVerified: Boolean
)