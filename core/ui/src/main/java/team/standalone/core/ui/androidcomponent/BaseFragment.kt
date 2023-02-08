package team.standalone.core.ui.androidcomponent

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import team.standalone.core.ui.dialog.SimpleLoadingDialog

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {
    protected lateinit var dataBinding: B
    private var loadingDialog: SimpleLoadingDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectData()
        observeDestinationResult()
    }

    protected open fun collectData() {}

    protected open fun observeDestinationResult() {}


    protected fun showLoadingDialog() {
        dismissLoadingDialog()
        loadingDialog = SimpleLoadingDialog.newInstance()
        loadingDialog?.isCancelable = false
        loadingDialog?.show(childFragmentManager, SimpleLoadingDialog::class.java.name)
    }

    protected fun dismissLoadingDialog() {
        loadingDialog?.apply { dismiss() }
        loadingDialog = null
    }
}