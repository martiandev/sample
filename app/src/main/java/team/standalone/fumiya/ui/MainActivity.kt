package team.standalone.fumiya.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.*
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import team.standalone.core.common.extension.convertDpToPx
import team.standalone.core.common.extension.gone
import team.standalone.core.common.extension.visible
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.ui.androidcomponent.BaseActivity
import team.standalone.core.ui.custom.CustomNavHostFragment
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.ActivityMainBinding
import team.standalone.fumiya.service.OneSignalService
import team.standalone.fumiya.service.OneSignalService.Companion.ACTION_CHAT
import team.standalone.fumiya.service.OneSignalService.Companion.ACTION_LIVE
import team.standalone.fumiya.service.OneSignalService.Companion.ACTION_PHOTO
import team.standalone.fumiya.service.OneSignalService.Companion.ACTION_VIDEO

@AndroidEntryPoint
@DelicateCoroutinesApi
class MainActivity : BaseActivity<ActivityMainBinding>() {
    companion object {
        private const val MARGIN_DEFAULT = 0
        private const val MARGIN_ERROR = 50
        fun newIntent(fragmentActivity: FragmentActivity) =
            Intent(fragmentActivity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
    }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navDestination: NavDestination
    private lateinit var topLevelDestination: Set<Int>
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = withDataBinding(R.layout.activity_main)
        setSupportActionBar(dataBinding.toolbar)

        topLevelDestination = setOf(
            R.id.navigation_home,
            R.id.navigation_park,
            R.id.navigation_live,
            R.id.navigation_chat,
            R.id.navigation_error_navigation
        )
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as CustomNavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.mobile_navigation)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            destinationChange(destination)
        }
        appBarConfiguration = AppBarConfiguration(topLevelDestination)

        setupActionBarWithNavController(navController, appBarConfiguration)
        dataBinding.navView.setupWithNavController(navController)

        setNotificationNavigation()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()


    override fun onResume() {
        super.onResume()
        dataBinding.container.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    override fun onPause() {
        dataBinding.container.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        super.onPause()
    }

    private fun setNotificationNavigation() {
        intent.extras?.let { extras ->
            when (extras.get(OneSignalService.ACTION)) {
                ACTION_CHAT -> R.id.navigation_chat
                ACTION_PHOTO, ACTION_VIDEO -> R.id.navigation_park
                ACTION_LIVE -> R.id.navigation_live
                else -> null
            }?.let { navigate ->
                dataBinding.navView.selectedItemId = navigate
            }
        }
    }

    private val listener = ViewTreeObserver.OnGlobalLayoutListener {
        if(::navDestination.isInitialized && navDestination.id in topLevelDestination) {
            val visibleBounds = Rect()
            dataBinding.container.getWindowVisibleDisplayFrame(visibleBounds)
            val keyboardHeight = dataBinding.container.height - visibleBounds.height()
            val marginOfError = this.convertDpToPx(MARGIN_ERROR)
            val params = dataBinding.navView.layoutParams as ViewGroup.MarginLayoutParams
            val marginBottom = when ( keyboardHeight > marginOfError) {
                true -> keyboardHeight - dataBinding.navView.height // Offset menu bar height.
                else -> MARGIN_DEFAULT
            }
            if(params.bottomMargin != marginBottom) {
                params.bottomMargin = marginBottom
                dataBinding.navView.layoutParams = params
            }
        }
    }

    fun navBarSelect(id: Int) {
        dataBinding.navView.selectedItemId = id
    }

    /**
     * Handles UI updates for destination change event.
     * */
    private fun destinationChange(destination: NavDestination) {
        if (destination.id in topLevelDestination) {
            if(destination.id != R.id.navigation_error_navigation) {
                navDestination = destination
            }
            dataBinding.navView.visible()
        } else {
            dataBinding.navView.gone()
        }
        actionBarVisibility(destination.id)
        setMenuItemColor(navDestination.id)
    }

    /**
     * Manages the visibility of the appbar.
     * Hide appbar when on base screen.
     * */
    private fun actionBarVisibility(id: Int) {
        supportActionBar?.let {
            when (id == R.id.navigation_chat) {
                true -> it.hide()
                else -> it.show()
            }
            if (id == R.id.navigation_error_navigation) {
                it.setDisplayHomeAsUpEnabled(false)
                it.title = resources.getString(
                    when (navDestination.id) {
                        R.id.navigation_park -> R.string.title_park
                        R.id.navigation_live -> R.string.title_station
                        R.id.navigation_chat -> R.string.title_base
                        else -> R.string.title_map
                    }
                )
            }
        }
    }

    /**
     * Set menu item icon color.
     * */
    private fun setMenuItemColor(id: Int) {
        val menuColorStates = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ),
            intArrayOf(
                getColor(R.color.colorButtonDisablePrimary),
                when (id) {
                    R.id.navigation_park -> getColor(R.color.colorMenuPark)
                    R.id.navigation_live -> getColor(R.color.colorMenuStation)
                    R.id.navigation_chat -> getColor(R.color.colorMenuBase)
                    else -> getColor(R.color.colorMenuMap)
                }
            )
        )
        dataBinding.navView.itemIconTintList = menuColorStates
        dataBinding.navView.itemTextColor = menuColorStates
    }

    /**
     * Refresh the current fragment's web view.
     * */
    fun refreshFragmentError() {
        dataBinding.navView.selectedItemId = navDestination.id
    }
}
