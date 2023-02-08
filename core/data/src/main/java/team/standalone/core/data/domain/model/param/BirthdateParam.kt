package team.standalone.core.data.domain.model.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BirthdateParam(
    val day: Int,
    val month: Int,
    val year: Int
) : Parcelable
