package team.standalone.core.ui.bindingadapter

import android.widget.EditText
import androidx.databinding.BindingAdapter
import team.standalone.core.ui.util.BindingAdapterUtil

object MaterialEditTextBindingAdapter {

    @JvmStatic
    @BindingAdapter(BindingAdapterUtil.TEXT_BIRTHDATE)
    fun setTextYear(view: EditText, value: Int) {
        if (value != 0) {
            view.setText(value.toString())
        } else {
            view.setText("")
        }
    }
}