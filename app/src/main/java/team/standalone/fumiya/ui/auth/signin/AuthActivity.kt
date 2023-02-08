package team.standalone.fumiya.ui.auth.signin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.ui.androidcomponent.BaseActivity
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.ActivityAuthBinding

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    companion object {
        fun newIntent(fragmentActivity: FragmentActivity) =
            Intent(fragmentActivity, AuthActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_auth) as NavHostFragment
        navController = navHostFragment.findNavController()

        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.signInFragment,
                R.id.signUpFragment,
                R.id.signUpVerificationFragment,
                R.id.signUpInputDetailsFragment,
                R.id.signUpPreviewDetailsFragment,
            ).build()

        setSupportActionBar(dataBinding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}