package team.standalone.core.network.model.request

import team.standalone.core.network.firebase.util.FirebaseProperty

data class UserRequest(
    val nickName: String,
    val firstName: String,
    val lastName: String,
    val firstNameKana: String,
    val lastNameKana: String,
    val gender: String,
    val phoneNumber: String,
    val birthdate: BirthdateRequest?,
    val address: AddressRequest,
) {
    fun toMap(): HashMap<String, Any> {
        val map = hashMapOf<String, Any>(
            FirebaseProperty.NICKNAME to nickName,
            FirebaseProperty.FIRST_NAME to firstName,
            FirebaseProperty.LAST_NAME to lastName,
            FirebaseProperty.FIRST_NAME_KANA to firstNameKana,
            FirebaseProperty.LAST_NAME_KANA to lastNameKana,
            FirebaseProperty.GENDER to gender,
            FirebaseProperty.PHONE_NUMBER to phoneNumber,
        )
        birthdate?.let {
            map.putAll(it.toMap())
        }
        map.putAll(address.toMap())
        return map
    }
}