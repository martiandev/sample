package team.standalone.core.common.exception

sealed class ArtistException (message: String) : Exception(message) {
    object ArtistNotFoundException : ArtistException("User is not an Artist")
}