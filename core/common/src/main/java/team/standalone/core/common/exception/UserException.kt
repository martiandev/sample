package team.standalone.core.common.exception

sealed class UserException(message: String) : Exception(message) {
    object UserNotFoundException : UserException("User not found")
    object UidNotFoundException : UserException("Uid not found")
    object EmailNotFoundException : UserException("Email not found")
    object InvalidFirstNameException : UserException("Invalid first name")
    object InvalidLastNameException : UserException("Invalid last name")
}