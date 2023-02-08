package team.standalone.core.network.model.request

data class SignInRequest(
    val email: String,
    val password: String,
)