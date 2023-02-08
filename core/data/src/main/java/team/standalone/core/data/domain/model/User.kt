package team.standalone.core.data.domain.model

import kotlinx.datetime.Instant

data class User(
    val uid: String,
    val email: String,
    val photoUrl: String?,
    val nickname: String,
    val firstName: String,
    val lastName: String,
    val firstNameKana: String,
    val lastNameKana: String,
    val memberNumber: String,
    val phoneNumber: String,
    val gender: Gender,
    val withdraw: Boolean,
    val birthdate: Birthdate,
    val address: Address,
    val subscription: Subscription,
    val createdAt: Instant?,
    val updatedAt: Instant?,
) {
    enum class Gender {
        MALE,
        FEMALE,
        NO_ANSWER,
        UNKNOWN
    }

    fun isMember(): Boolean =
        if (subscription.hasFirstSubscription()) subscription.subscribed else false
}
