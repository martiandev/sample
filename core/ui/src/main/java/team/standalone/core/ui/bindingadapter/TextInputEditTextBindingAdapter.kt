package team.standalone.core.ui.bindingadapter

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import team.standalone.core.common.util.Lumberjack
import team.standalone.core.data.domain.model.param.BirthdateParam
import team.standalone.core.ui.util.BindingAdapterUtil
import java.text.NumberFormat

object TextInputEditTextBindingAdapter {
    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.NUMBER,
            BindingAdapterUtil.NUMBER_GROUPED,
        ],
        requireAll = true
    )
    fun bindNumber(textInputEditText: TextInputEditText, number: Number?, numberGrouped: Boolean) {
        val numberFormat = NumberFormat.getNumberInstance()
        numberFormat.isGroupingUsed = numberGrouped

        if (number != null) {
            textInputEditText.setText(numberFormat.format(number))
        } else {
            textInputEditText.text = null
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.BIRTHDATE_YEAR
        ],
        requireAll = true
    )
    fun bindBirthdateYear(
        textInputEditText: TextInputEditText,
        birthdateParam: BirthdateParam?,
    ) {
        textInputEditText.setText(birthdateParam?.year?.toString())
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.BIRTHDATE_MONTH
        ],
        requireAll = true
    )
    fun bindBirthdateMonth(
        textInputEditText: TextInputEditText,
        birthdateParam: BirthdateParam?,
    ) {
        textInputEditText.setText(birthdateParam?.month?.toString())
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.BIRTHDATE_DAY
        ],
        requireAll = true
    )
    fun bindBirthdateDay(
        textInputEditText: TextInputEditText,
        birthdateParam: BirthdateParam?,
    ) {
        val day = birthdateParam?.day?.toString()
        textInputEditText.setText(birthdateParam?.day?.toString())
    }
}