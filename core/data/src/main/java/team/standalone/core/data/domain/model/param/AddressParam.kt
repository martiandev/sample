package team.standalone.core.data.domain.model.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressParam(
    val number: String,
    val city: String,
    val prefecture: String,
    val structure: String,
    val postalCode: String
) : Parcelable
