package team.standalone.core.ui.bindingadapter

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import team.standalone.core.common.extension.gone
import team.standalone.core.common.extension.invisible
import team.standalone.core.common.extension.visible
import team.standalone.core.data.domain.model.param.BirthdateParam
import team.standalone.core.ui.util.BindingAdapterUtil

object ViewBindingAdapter {
    @JvmStatic
    @BindingAdapter(BindingAdapterUtil.VISIBLE_GONE)
    fun bindVisibleGone(view: View, isVisible: Boolean) {
        if (isVisible) view.visible() else view.gone()
    }

    @JvmStatic
    @BindingAdapter(BindingAdapterUtil.VISIBLE_HIDDEN)
    fun bindVisibleHidden(view: View, isVisible: Boolean) {
        if (isVisible) view.visible() else view.invisible()
    }

    @JvmStatic
    @BindingAdapter(BindingAdapterUtil.VISIBLE_IF_NOT_EMPTY)
    fun bindVisibleIfNotEmpty(view: View, value: String) {
        if (value.isNotEmpty()) view.visible() else view.gone()
    }

    @JvmStatic
    @BindingAdapter(BindingAdapterUtil.VISIBLE_IF_NO_SELECTED)
    fun bindVisibleIfNoSelected(view: View, value: Int) {
        if (value != 0) view.visible() else view.gone()
    }

    @JvmStatic
    @BindingAdapter(BindingAdapterUtil.VISIBLE_IF_NOT_NULL)
    fun bindVisibleIfNotNull(view: View, value: BirthdateParam?) {
        if (value != null) view.visible() else view.gone()
    }

    @JvmStatic
    @BindingAdapter(BindingAdapterUtil.UNDERLINE_TEXT)
    fun bindUnderlineText(textView: TextView, isUnderlined: Boolean) {
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
}