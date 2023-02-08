package team.standalone.core.ui.androidcomponent

import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment<B : ViewDataBinding> : DialogFragment() {
    protected lateinit var dataBinding: B

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectData()
    }

    protected open fun collectData() {}

    protected fun getScreenDimension(): Point? {
        return dialog?.window?.run {
            val size = Point()
            val display = windowManager.defaultDisplay
            display.getSize(size)
            size
        }
    }

    protected open fun getWidth(): Int {
        val screenWidth = getScreenDimension()?.x ?: 0
        return (screenWidth * .90).toInt()
    }

    protected open fun getHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }
}