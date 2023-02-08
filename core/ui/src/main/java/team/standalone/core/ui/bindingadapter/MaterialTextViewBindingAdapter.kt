package team.standalone.core.ui.bindingadapter

import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import kotlinx.datetime.Instant
import team.standalone.core.common.extension.gone
import team.standalone.core.common.extension.visible
import team.standalone.core.ui.util.BindingAdapterUtil
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object MaterialTextViewBindingAdapter {
    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.FIRST_NAME,
            BindingAdapterUtil.LAST_NAME
        ],
        requireAll = true
    )
    fun bindFullName(materialTextView: MaterialTextView, firstName: String?, lastName: String?) {
        val fullName = "${firstName.orEmpty()} ${lastName.orEmpty()}".trim()
        materialTextView.text = fullName
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.JAP_FORMAT_FIRST_NAME,
            BindingAdapterUtil.JAP_FORMAT_LAST_NAME,
            BindingAdapterUtil.JAP_FORMAT_SALUTATION
        ],
        requireAll = true
    )
    fun bindJapFormatFullName(
        materialTextView: MaterialTextView,
        japFormatFirstName: String?,
        japFormatLastName: String?,
        japFormatSalutation: String?,
    ) {
        val fullName =
            "${japFormatLastName.orEmpty()} ${japFormatFirstName.orEmpty()} ${japFormatSalutation.orEmpty()}".trim()
        materialTextView.text = fullName
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.INSTANT_DATE_SHORT
        ]
    )
    fun bindInstantDate(materialTextView: MaterialTextView, instant: Instant?) {
        materialTextView.text = if (instant != null) {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = Date(instant.toEpochMilliseconds())
            format.format(date)
        } else null
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.FORM_HAS_ERROR,
            BindingAdapterUtil.FORM_ERROR],
        requireAll = true
    )
    fun bindFormItem(
        materialTextView: MaterialTextView,
        formHasError: Boolean,
        formError: String?,
    ) {
        materialTextView.apply {
            text = formError
            if (formHasError) visible() else gone()
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.NUMBER,
            BindingAdapterUtil.NUMBER_GROUPED,
        ],
        requireAll = true
    )
    fun bindNumber(materialTextView: MaterialTextView, number: Number?, numberGrouped: Boolean) {
        val numberFormat = NumberFormat.getNumberInstance()
        numberFormat.isGroupingUsed = numberGrouped

        if (number != null) {
            materialTextView.text = numberFormat.format(number)
        } else {
            materialTextView.text = null
        }
    }
}