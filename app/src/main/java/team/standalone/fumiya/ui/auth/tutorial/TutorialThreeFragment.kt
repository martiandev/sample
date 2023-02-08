package team.standalone.fumiya.ui.auth.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.fumiya.databinding.FragmentTutorialThreeBinding
import team.standalone.fumiya.ui.auth.signin.AuthActivity

@AndroidEntryPoint
class TutorialThreeFragment : BaseFragment<FragmentTutorialThreeBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            withDataBinding(
                fragment = this,
                binding = FragmentTutorialThreeBinding.inflate(inflater)
            )
        dataBinding.fragment = this
        return dataBinding.root
    }

    fun toAuthActivity() {
        startActivity(AuthActivity.newIntent(requireActivity()))
        requireActivity().finish()
    }
}