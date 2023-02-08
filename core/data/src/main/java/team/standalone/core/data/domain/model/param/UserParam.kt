package team.standalone.core.data.domain.model.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import team.standalone.core.data.domain.model.User

@Parcelize
data class UserParam(
    val nickName: String,
    val firstName: String,
    val lastName: String,
    val firstNameKana: String,
    val lastNameKana: String,
    val gender: User.Gender,
    val phoneNumber: String,
    val birthdate: BirthdateParam?,
    val address: AddressParam,
) : Parcelable
