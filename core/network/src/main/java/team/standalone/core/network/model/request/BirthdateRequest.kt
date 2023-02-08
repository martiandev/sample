package team.standalone.core.network.model.request

import team.standalone.core.network.firebase.util.FirebaseProperty

data class BirthdateRequest(
    val day: Int,
    val month: Int,
    val year: Int
) {
    fun toMap(): HashMap<String, Any> {
        return hashMapOf(
            FirebaseProperty.BIRTH_DAY to day,
            FirebaseProperty.BIRTH_MONTH to month,
            FirebaseProperty.BIRTH_YEAR to year,
        )
    }
}