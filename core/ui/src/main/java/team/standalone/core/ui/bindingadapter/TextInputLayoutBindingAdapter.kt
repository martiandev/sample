package team.standalone.core.ui.bindingadapter

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import team.standalone.core.ui.util.BindingAdapterUtil

object TextInputLayoutBindingAdapter {
    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.FORM_ERROR,
            BindingAdapterUtil.FORM_HAS_ERROR
        ],
        requireAll = true
    )
    fun bindFormItem(
        textInputLayout: TextInputLayout,
        formError: String?,
        formHasError: Boolean,
    ) {
        textInputLayout.apply {
            isErrorEnabled = formHasError
            error = if (isErrorEnabled) formError else null
        }
    }

    @JvmStatic
    @BindingAdapter(BindingAdapterUtil.ERROR_TEXT)
    fun setErrorText(view: TextInputLayout, errorMessage: String?) {
        view.error = errorMessage
    }

}