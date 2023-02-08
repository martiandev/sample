package team.standalone.core.ui.bindingadapter

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import team.standalone.core.ui.util.BindingAdapterUtil

object MaterialAutoCompleteTextViewBindingAdapter {
    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.SELECTED_TEXT
        ],
        requireAll = true
    )
    fun bindSelectedText(
        materialAutoCompleteTextView: MaterialAutoCompleteTextView,
        selectedText: String?,
    ) {
        materialAutoCompleteTextView.setText(selectedText, false)
    }
}