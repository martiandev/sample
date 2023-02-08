package team.standalone.feature.userinfo.ui.updateuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.observeDialogResult
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.MessageUtil
import team.standalone.core.common.util.navigate
import team.standalone.core.data.domain.model.param.BirthdateParam
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.core.ui.util.NavKeys
import team.standalone.feature.userinfo.R
import team.standalone.feature.userinfo.databinding.UserInfoFragmentUpdateUserBinding

@AndroidEntryPoint
class UpdateUserFragment : BaseFragment<UserInfoFragmentUpdateUserBinding>() {
    private val updateUserViewModel: UpdateUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = UserInfoFragmentUpdateUserBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = updateUserViewModel
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPrefectureList()
    }

    private fun setupPrefectureList() {
        val prefectureList =
            resources.getStringArray(team.standalone.core.ui.R.array.prefecture_list)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            prefectureList
        )
        dataBinding.autoCompleteTextViewPrefecture.apply {
            setAdapter(adapter)
            setOnItemClickListener { _, _, index, _ ->
                updateUserViewModel.addressPrefecture.input.update { prefectureList[index] }
            }
        }
    }

    override fun collectData() {
        collectLatestLifecycleFlow(updateUserViewModel.uiState) { uiState ->
            uiState.userState.data?.let { updateUserViewModel.initForm(it) }
        }
        collectLatestLifecycleFlow(updateUserViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                is UpdateUserUiEvent.NavigateToSelectBirthdate -> {
                    UpdateUserFragmentDirections.actionUpdateUserToSelectBirthdate(uiEvent.param)
                        .navigate(findNavController())
                }
            }
        }
        collectLatestLifecycleFlow(updateUserViewModel.updateUserResult) { result ->
            when (result) {
                is LoadResult.Loading -> {
                    showLoadingDialog()
                }
                is LoadResult.Success -> {
                    dismissLoadingDialog()
                    MessageUtil.toast(
                        context = requireContext(),
                        textResId = team.standalone.core.ui.R.string.uc_update_user_success
                    )
                    findNavController().popBackStack()
                }
                is LoadResult.Error -> {
                    dismissLoadingDialog()
                    val errorMessage = when (result.exception) {
                        is AuthException.NetworkException -> getString(team.standalone.core.ui.R.string.uc_network_error)
                        else -> getString(team.standalone.core.ui.R.string.uc_update_user_failed)
                    }
                    MessageUtil.toast(
                        context = requireContext(),
                        text = errorMessage
                    )
                }
            }
        }
        collectLatestLifecycleFlow(updateUserViewModel.enableUpdate) { enabled ->
            dataBinding.buttonUpdateUser.isEnabled = enabled
        }
    }

    protected fun handleError(exception: Exception) {

    }

    override fun observeDestinationResult() {
        observeDialogResult<BirthdateParam>(
            key = NavKeys.BIRTHDATE_PARAM,
            destinationId = R.id.updateUserFragment
        ) { birthdateParam ->
            updateUserViewModel.updateBirthdate(birthdateParam)
        }
    }
}