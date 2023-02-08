package team.standalone.fumiya.ui.auth.tutorial

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.ui.androidcomponent.BaseActivity
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.ActivityTutorialBinding
import team.standalone.fumiya.ui.MainActivity

@AndroidEntryPoint
class TutorialActivity : BaseActivity<ActivityTutorialBinding>() {

    private val tutorialViewModel: TutorialViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_tutorial) as NavHostFragment
        navController = navHostFragment.findNavController()

        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.tutorialOneFragment,
                R.id.tutorialTwoFragment,
                R.id.tutorialThreeFragment
            ).build()

        setSupportActionBar(dataBinding.toolbar)
        dataBinding.toolbarTitle.text = getString(R.string.tutorial_fragment_member_only_content)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.tutorialOneFragment) supportActionBar?.hide()
            else supportActionBar?.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun collectData() {
        collectLatestLifecycleFlow(tutorialViewModel.authenticateResult) { user ->
            when (user) {
                is LoadResult.Loading -> {
                    dataBinding.linearPbBackground.visibility = View.VISIBLE
                    dataBinding.progressBar.visibility = View.VISIBLE
                }
                is LoadResult.Success -> {
                    startActivity(MainActivity.newIntent(this))
                    finish()
                }
                else -> {
                    dataBinding.linearPbBackground.visibility = View.GONE
                    dataBinding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}