package team.standalone.feature.resetpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.feature.resetpassword.databinding.FragmentResetPasswordVerificationBinding

@AndroidEntryPoint
class ResetPasswordVerificationFragment :
    BaseFragment<FragmentResetPasswordVerificationBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = FragmentResetPasswordVerificationBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(
                false
            )
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateToTopPage()
                }
            })
        return dataBinding.root
    }

    fun onBack() {
        navigateToTopPage()
    }

    fun navigateToTopPage() {
        findNavController().popBackStack()
    }

}