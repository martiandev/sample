package team.standalone.core.network.model.request

import team.standalone.core.network.firebase.util.FirebaseProperty

data class AddressRequest(
    val structure: String,
    val number: String,
    val city: String,
    val prefecture: String,
    val postalCode: String
) {
    fun toMap(): HashMap<String, Any> {
        return hashMapOf(
            FirebaseProperty.ADDRESS_STRUCTURE to structure,
            FirebaseProperty.ADDRESS_NUMBER to number,
            FirebaseProperty.ADDRESS_CITY to city,
            FirebaseProperty.ADDRESS_PREFECTURE to prefecture,
            FirebaseProperty.ADDRESS_POSTAL_CODE to postalCode,
        )
    }
}