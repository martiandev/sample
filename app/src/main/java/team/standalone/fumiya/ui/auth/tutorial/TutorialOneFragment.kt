package team.standalone.fumiya.ui.auth.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.navigate
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.fumiya.databinding.FragmentTutorialOneBinding

@AndroidEntryPoint
class TutorialOneFragment : BaseFragment<FragmentTutorialOneBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            withDataBinding(
                fragment = this,
                binding = FragmentTutorialOneBinding.inflate(inflater)
            )
        dataBinding.fragment = this
        return dataBinding.root
    }

    fun toSecondScreen() {
        TutorialOneFragmentDirections
            .actionTutorialOneFragmentToTutorialTwoFragment()
            .navigate(findNavController())
    }
}