package team.standalone.core.common.util

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

inline fun <T : ViewModel> subscribeToViewModel(viewModel: T, block: T.() -> Unit) {
    viewModel.block()
}

fun NavDirections.navigate(navController: NavController, navOptions: NavOptions? = null) {
    try {
        navController.navigate(this, navOptions)
    } catch (e: Exception) {
        Lumberjack.error(e)
    }
}

fun NavDeepLinkRequest.navigate(navController: NavController, navOptions: NavOptions? = null) {
    try {
        navController.navigate(this, navOptions)
    } catch (e: Exception) {
        Lumberjack.error(e)
    }
}