package team.standalone.fumiya.ui.auth.signup.signupverification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.IntentUtil
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.MessageUtil
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.FragmentSignUpVerificationBinding
import team.standalone.fumiya.ui.auth.signup.signupverification.SignUpVerificationUiEvent.NavigateToLogin

@AndroidEntryPoint
class SignUpVerificationFragment : BaseFragment<FragmentSignUpVerificationBinding>() {

    private val signUpVerificationViewModel: SignUpVerificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = FragmentSignUpVerificationBinding.inflate(inflater)
        )
        dataBinding.viewModel = signUpVerificationViewModel

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        return dataBinding.root
    }

    override fun collectData() {
        collectLatestLifecycleFlow(signUpVerificationViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                NavigateToLogin -> findNavController().popBackStack()
                is SignUpVerificationUiEvent.NavigateToBrowser -> IntentUtil.getBrowserIntent(
                    uiEvent.url
                )?.let(::startActivity)
            }
        }

        collectLatestLifecycleFlow(signUpVerificationViewModel.verificationResult) {
            when (it) {
                is LoadResult.Loading ->
                    dataBinding.progressBar.visibility = View.VISIBLE
                is LoadResult.Success -> {
                    dataBinding.progressBar.visibility = View.GONE
                    MessageUtil.toast(
                        requireActivity(),
                        getString(R.string.fragment_verification_authentication_sent)
                    )
                }
                is LoadResult.Error -> {
                    dataBinding.progressBar.visibility = View.GONE
                    MessageUtil.toast(
                        requireActivity(),
                        getString(R.string.fragment_verification_authentication_failed)
                    )
                }
            }
        }
    }
}