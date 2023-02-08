package team.standalone.fumiya.ui.util.error

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.navigate
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.FragmentErrorNetworkBinding
import team.standalone.fumiya.ui.MainActivity

@AndroidEntryPoint
@DelicateCoroutinesApi
class ErrorNetworkFragment: BaseFragment<FragmentErrorNetworkBinding>() {

    private val errorViewModel: ErrorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = FragmentErrorNetworkBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = errorViewModel
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenuOptions()
    }

    /**
     * Method for handling flow lifecycle data collection.
     * */
    override fun collectData() {
        collectLatestLifecycleFlow(errorViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                NetworkErrorUiEvent.ActionRefresh ->
                    (activity as? MainActivity)?.refreshFragmentError()
            }
        }
    }

    /**
     * Setup menu options.
     * */
    private fun setupMenuOptions() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_options_special, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    R.id.menu_options_other -> {
                        ErrorNetworkFragmentDirections.actionGlobalNavigationOther()
                            .navigate(findNavController())
                        true
                    }
                    else -> true
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}