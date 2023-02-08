package team.standalone.core.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.ui.androidcomponent.BaseDialogFragment
import team.standalone.core.ui.databinding.DialogLoadingBinding

@AndroidEntryPoint
class LoadingDialog : BaseDialogFragment<DialogLoadingBinding>() {
    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_MESSAGE = "arg_message"
        fun newInstance(title: String, message: String) =
            LoadingDialog().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_MESSAGE, message)
                }
            }
    }

    private lateinit var title: String
    private lateinit var message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = requireArguments().getString(ARG_TITLE).orEmpty()
        message = requireArguments().getString(ARG_MESSAGE).orEmpty()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = DialogLoadingBinding.inflate(inflater)
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.title = title
        dataBinding.message = message
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
        val screenWidth = getScreenDimension()?.x ?: 0
        return (screenWidth * .90).toInt()
    }

    override fun getHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }
}