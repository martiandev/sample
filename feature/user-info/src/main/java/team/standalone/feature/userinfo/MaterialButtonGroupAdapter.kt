package team.standalone.feature.userinfo

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.coroutines.flow.MutableStateFlow
import team.standalone.core.data.domain.model.User

@BindingAdapter("genderFilter")
fun MaterialButtonToggleGroup.setCheckedButton(gender: MutableStateFlow<User.Gender?>) {
    val selectedId = when (gender.value) {
        User.Gender.MALE -> R.id.materialButtonMale
        User.Gender.FEMALE -> R.id.materialButtonFemale
        User.Gender.NO_ANSWER -> R.id.materialButtonNoAnswer
        else -> null
    }

    if (selectedId != null && selectedId != checkedButtonId) {
        check(selectedId)
    }
}

@InverseBindingAdapter(attribute = "genderFilter")
fun MaterialButtonToggleGroup.getCheckedButton(): User.Gender? {
    return when (checkedButtonId) {
        R.id.materialButtonMale -> User.Gender.MALE
        R.id.materialButtonFemale -> User.Gender.FEMALE
        R.id.materialButtonNoAnswer -> User.Gender.NO_ANSWER
        else -> null
    }
}

@BindingAdapter("app:genderFilterAttrChanged")
fun MaterialButtonToggleGroup.setListeners(listener: InverseBindingListener?) {
    listener?.let {
        addOnButtonCheckedListener { _, _, _ ->
            listener.onChange()
        }
    }
}
