package team.standalone.core.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.ui.androidcomponent.BaseDialogFragment
import team.standalone.core.ui.databinding.DialogLoadingBinding
import team.standalone.core.ui.databinding.DialogSimpleLoadingBinding

@AndroidEntryPoint
class SimpleLoadingDialog : BaseDialogFragment<DialogSimpleLoadingBinding>() {
    companion object {
        fun newInstance() = SimpleLoadingDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = DialogSimpleLoadingBinding.inflate(inflater)
        )
        return dataBinding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setGravity(Gravity.CENTER)
            setLayout(getWidth(), getHeight())
        }
    }

    override fun getWidth(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    override fun getHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }
}