package team.standalone.feature.userinfo.ui.resetpasswordfinish

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.qualifier.ActivityNavigation
import team.standalone.core.common.qualifier.AppActivity
import team.standalone.core.common.util.navigate
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.feature.userinfo.databinding.UserInfoFragmentResetPasswordFinishBinding
import team.standalone.feature.userinfo.ui.resetpasswordfinish.ResetPasswordFinishUiEvent.NavigateToSignIn
import javax.inject.Inject

@AndroidEntryPoint
class ResetPasswordFinishFragment : BaseFragment<UserInfoFragmentResetPasswordFinishBinding>() {
    private val resetPasswordFinishViewModel: ResetPasswordFinishViewModel by viewModels()

    @ActivityNavigation(AppActivity.AUTH)
    @Inject
    lateinit var authIntent: Intent

    private val loginLauncher =
        registerForActivityResult(StartActivityForResult()) {
            ResetPasswordFinishFragmentDirections.actionPopupToUserInfo()
                .navigate(findNavController())
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = UserInfoFragmentResetPasswordFinishBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = resetPasswordFinishViewModel
        return dataBinding.root
    }

    override fun collectData() {
        collectLatestLifecycleFlow(resetPasswordFinishViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                NavigateToSignIn -> loginLauncher.launch(authIntent)
            }
        }
    }
}