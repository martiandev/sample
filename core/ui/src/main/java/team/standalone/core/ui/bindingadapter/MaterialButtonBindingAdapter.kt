package team.standalone.core.ui.bindingadapter

import android.graphics.Paint
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import team.standalone.core.ui.util.BindingAdapterUtil

object MaterialButtonBindingAdapter {
    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.IS_UNDERLINED
        ]
    )
    fun bindIsUnderlined(materialButton: MaterialButton, isUnderlined: Boolean) {
        materialButton.paintFlags = materialButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
}