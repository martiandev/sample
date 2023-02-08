package team.standalone.fumiya.ui.park.tab

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import team.standalone.core.common.extension.*
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.navigate
import team.standalone.core.data.domain.model.User
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.feature.billing.BillingSubscriptionActivity
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.FragmentParkBinding
import team.standalone.fumiya.service.OneSignalService
import team.standalone.fumiya.ui.MainActivity
import team.standalone.fumiya.ui.auth.signin.AuthActivity
import team.standalone.fumiya.ui.park.photo.PhotoFragment
import team.standalone.fumiya.ui.park.tab.ParkUiEvent.*
import team.standalone.fumiya.ui.park.video.VideoFragment

@AndroidEntryPoint
@DelicateCoroutinesApi
class ParkFragment : BaseFragment<FragmentParkBinding>() {

    private val parkViewModel: ParkViewModel by viewModels()
    private lateinit var fragments: ArrayList<ParkUiEvent>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = FragmentParkBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = parkViewModel
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragments = arrayListOf(PhotoTab, VideoTab)
        getIntentExtra()
        setupMenuOptions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isContentShown(isShown = false)
        viewModelStore.clear()
    }

    override fun collectData() {
        collectLatestLifecycleFlow(parkViewModel.uiState) { uiState ->
            when (uiState.userState) {
                is LoadResult.Loading -> { }
                is LoadResult.Success, is LoadResult.Error -> {
                    checkIfAllowAccess(uiState.userState.data)
                }
            }
        }
    }

    /**
     * Get if user is allowed to access park screen.
     * If not, will redirect to MemberSubscriptionActivity or AuthActivity.
     * */
    private fun checkIfAllowAccess(user: User?) {
        when (user == null || !user.subscription.subscribed) {
            true -> navigatePermissionDeny(user)
            false -> managePageTab()
        }
    }

    /**
     * Navigate user out of PARK screen if permission is denied.
     * */
    private fun navigatePermissionDeny(user: User?) {
        val navigate = when (user == null) {
            true -> AuthActivity::class.java
            false -> BillingSubscriptionActivity::class.java
        }
        parkViewModel.navigateToActivity(
            fragment = this,
            Intent(context, navigate)
        )
        (requireActivity() as? MainActivity)?.navBarSelect(R.id.navigation_home)
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
                        ParkFragmentDirections.actionGlobalNavigationOther()
                            .navigate(findNavController())
                        true
                    }
                    else -> true
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    /**
     * Manages tab view for the park screen.
     * */
    private fun managePageTab() {
        if(dataBinding.tabContainer.isVisible) return // Avoid re-initialization of tab and adapter.

        dataBinding.parkPager.apply {
            adapter = object : FragmentStateAdapter(this@ParkFragment) {
                override fun getItemCount(): Int = fragments.size
                override fun createFragment(position: Int): Fragment {
                    return when (position) {
                        fragments.indexOf(PhotoTab) -> PhotoFragment()
                        else -> VideoFragment()
                    }
                }
            }
            isUserInputEnabled = false
        }
        TabLayoutMediator(dataBinding.parkTab, dataBinding.parkPager) { tab, position ->
            when (position) {
                fragments.indexOf(PhotoTab) -> {
                    tab.text = resources.getString(R.string.title_photo)
                }
                fragments.indexOf(VideoTab) -> {
                    tab.text = resources.getString(R.string.title_video)
                }
            }
        }.attach()
        isContentShown()
    }

    /**
     * Method for showing park content.
     * */
    private fun isContentShown(isShown: Boolean = true) {
        dataBinding.tabContainer.makeGoneIf { !isShown }
        dataBinding.progressBar.makeGoneIf { isShown }
    }

    /**
     * Get extra values for specific park navigation.
     * */
    private fun getIntentExtra() {
        activity?.intent?.extras?.let {
            val action = it.get(OneSignalService.ACTION)
            if(action != null && action == OneSignalService.ACTION_VIDEO) {
                activity?.intent?.removeExtra(OneSignalService.ACTION)
                dataBinding.parkTab.getTabAt(fragments.indexOf(VideoTab))?.select()
            }
        }
    }
}