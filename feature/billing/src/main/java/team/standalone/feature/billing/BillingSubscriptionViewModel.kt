package team.standalone.feature.billing

import android.app.Activity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import team.standalone.core.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
@DelicateCoroutinesApi
class BillingSubscriptionViewModel @Inject constructor(
    private val billingSubscriptionManager: BillingSubscriptionManager,
) : BaseViewModel<BillingUiEvent>(), DefaultLifecycleObserver {

    val loading = billingSubscriptionManager.loading
    val subscription = billingSubscriptionManager.subResult

    override fun onCreate(owner: LifecycleOwner) {
        billingSubscriptionManager.onCreate()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        billingSubscriptionManager.onDestroy()
    }

    fun onPurchase(activity: Activity) {
        billingSubscriptionManager.launchPurchaseFlow(activity)
    }
}

sealed class BillingUiEvent