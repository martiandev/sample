package team.standalone.core.common.exception

sealed class AuthException(message: String) : Exception(message) {
    object UnauthorizedException : AuthException("Unauthorized access")
    object EmailNotVerifiedException : AuthException("Email not verified")
    object InvalidCredentialsException :
        AuthException("The password is invalid or the user does not have a password.")

    object InvalidUserException :
        AuthException("There is no user record corresponding to this identifier. The user may have been deleted.")

    object NetworkException : AuthException("A network error")

    object EmailExistedException : AuthException("The email address is already in use by another account.")

}