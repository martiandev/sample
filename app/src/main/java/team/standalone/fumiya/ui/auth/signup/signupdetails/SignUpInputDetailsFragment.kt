package team.standalone.fumiya.ui.auth.signup.signupdetails

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.observeDialogResult
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.navigate
import team.standalone.core.data.domain.model.param.BirthdateParam
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.core.ui.manager.DeepLinkManager
import team.standalone.core.ui.util.NavKeys
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.FragmentSignUpInputDetailsBinding
import team.standalone.fumiya.ui.auth.signup.signupdetails.SignUpInputDetailsUiEvent.*

@AndroidEntryPoint
class SignUpInputDetailsFragment : BaseFragment<FragmentSignUpInputDetailsBinding>() {
    private val signUpInputDetailsViewModel: SignUpInputDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = FragmentSignUpInputDetailsBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = signUpInputDetailsViewModel

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                   findNavController().popBackStack()
                }
            })
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPrefectureList()
    }

    private fun setupPrefectureList() {
        val prefectureList = resources.getStringArray(team.standalone.core.ui.R.array.prefecture_list)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            prefectureList
        )
        dataBinding.spAddress.apply {
            setAdapter(adapter)
            setOnItemClickListener { _, _, index, _ ->
                signUpInputDetailsViewModel.addressPrefecture.input.set(prefectureList[index])
            }
        }
    }

    override fun collectData() {
        collectLatestLifecycleFlow(signUpInputDetailsViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                is NavigateToPreviewDetails -> {
                    SignUpInputDetailsFragmentDirections
                        .actionSignUpInputDetailsFragmentToSignUpPreviewDetailsFragment(uiEvent.userParam)
                        .navigate(findNavController())
                }
                is NavigateToSelectBirthdate -> {
                    SignUpInputDetailsFragmentDirections
                        .actionSignUpInputDetailsFragmentToSelectBirthdateDialog(uiEvent.param)
                        .navigate(findNavController())
                }
                is NavigateToPrivacyPolicy -> {
                    val title = getString(R.string.fragment_sign_up_title_privacy_policy)
                    val uri = DeepLinkManager.privacyPolicy(title)
                    displaysLocalHtmlFile(uri)
                }
                NavigateToAppTerms -> {
                    val uri = DeepLinkManager.termsOfService()
                    displaysLocalHtmlFile(uri)
                }
            }
        }
    }

    override fun observeDestinationResult() {
        observeDialogResult<BirthdateParam>(
            key = NavKeys.BIRTHDATE_PARAM,
            destinationId = R.id.signUpInputDetailsFragment
        ) { birthdateParam ->
            signUpInputDetailsViewModel.birthdate.input.set(birthdateParam)
        }
    }

    private fun displaysLocalHtmlFile(uri: Uri) {
        val request: NavDeepLinkRequest = NavDeepLinkRequest.Builder
            .fromUri(uri)
            .build()
        val navOptions = NavOptions.Builder()
            .setEnterAnim(team.standalone.core.ui.R.anim.enter_from_right)
            .setExitAnim(team.standalone.core.ui.R.anim.exit_to_left)
            .setPopEnterAnim(team.standalone.core.ui.R.anim.enter_from_left)
            .setPopExitAnim(team.standalone.core.ui.R.anim.exit_to_right)
            .build()
        request.navigate(findNavController(), navOptions)
    }
}