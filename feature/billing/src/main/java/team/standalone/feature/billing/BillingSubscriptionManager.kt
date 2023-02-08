package team.standalone.feature.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import team.standalone.core.common.model.PurchaseResult
import team.standalone.core.common.qualifier.BillingItemSubscribe
import team.standalone.core.common.util.Result
import team.standalone.core.data.domain.usecase.MemberSubscribeUseCase
import javax.inject.Inject
import javax.inject.Singleton

@DelicateCoroutinesApi
@Singleton
class BillingSubscriptionManager @Inject constructor(
    @ApplicationContext private val context: Context,
    @BillingItemSubscribe private val billingItemSubscribe: String,
    private val subscribeUseCase: MemberSubscribeUseCase,
) : PurchasesUpdatedListener, ProductDetailsResponseListener, PurchasesResponseListener {

    private lateinit var billingClient: BillingClient

    private val _productDetailsMap: HashMap<String, ProductDetails> = HashMap()

    private val _loading = MutableSharedFlow<Boolean>()
    val loading = _loading.asSharedFlow()
    private val _subResult = MutableSharedFlow<PurchaseResult?>()
    val subResult = _subResult.asSharedFlow()

    /**initialize billing client, listened to purchase update callback [onPurchasesUpdated]*/
    fun onCreate() {
        billingClient = BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases()
            .build()
        if (!billingClient.isReady) {
            establishConnection()
        }
    }

    /*establish connection to google play*/
    private fun establishConnection() {
        with(billingClient) {
            startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(result: BillingResult) {
                    if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                        queryProductDetails()
                        queryPurchases()
                    }
                }

                override fun onBillingServiceDisconnected() {
                    establishConnection()
                }
            })
        }
    }

    /**query the existing/pending purchases: New purchases will be provided to the [onPurchasesUpdated].*/
    fun queryPurchases() {
        if (!billingClient.isReady) {
            establishConnection()
        }

        billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.SUBS)
                .build(), this
        )
    }

    /**callback from the billing library when queryPurchasesAsync is called in the [queryPurchases].*/
    override fun onQueryPurchasesResponse(
        billingResult: BillingResult,
        purchaseList: MutableList<Purchase>
    ) {
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK ->
                for (purchase in purchaseList) {
                    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {
                        handlePurchase(purchase)
                    }
                }
        }
    }

    /**launching the purchase flow*/
    fun launchPurchaseFlow(activity: Activity) {
        if (billingClient.isReady) {
            _productDetailsMap[billingItemSubscribe]
                ?.let { productDetails ->
                    val productDetailsParamsList = listOf(
                        productDetails.subscriptionOfferDetails?.get(0)?.offerToken?.let { token ->
                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .setOfferToken(token)
                                .build()
                        }
                    )

                    billingClient.launchBillingFlow(
                        activity, BillingFlowParams.newBuilder()
                            .setProductDetailsParamsList(productDetailsParamsList)
                            .build()
                    )
                }
        }
    }

    /**called by the Billing Library when new purchases are detected.*/
    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchaseList: MutableList<Purchase>?
    ) {
        val resultCode = billingResult.responseCode
        if (resultCode == BillingClient.BillingResponseCode.OK && purchaseList != null) {
            for (purchase in purchaseList) {
                handlePurchase(purchase)
            }
        } else {
            when (resultCode) {
                BillingClient.BillingResponseCode.USER_CANCELED -> {
                    GlobalScope.launch { _subResult.emit(PurchaseResult.Cancelled) }
                }
                BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> {
                    GlobalScope.launch { _subResult.emit(PurchaseResult.AlreadyOwned) }
                }
                else -> {
                    GlobalScope.launch { _subResult.emit(PurchaseResult.Failed) }
                }
            }
        }
    }

    /**query product details: In order to make purchases, you need the [ProductDetails] subscription.*/
    private fun queryProductDetails() {
        val productList: MutableList<QueryProductDetailsParams.Product> = arrayListOf()
        productList.add(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(billingItemSubscribe)
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        )

        /**this is an asynchronous call that will receive a result in [onProductDetailsResponse]. */
        QueryProductDetailsParams.newBuilder().setProductList(productList)
            .let { productDetailsParams ->
                billingClient.queryProductDetailsAsync(productDetailsParams.build(), this)
            }
    }

    /**receives the result from [queryProductDetails] from the asynchronous call queryProductDetailsAsync*/
    override fun onProductDetailsResponse(
        billingResult: BillingResult,
        productDetailsList: MutableList<ProductDetails>
    ) {
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                if (productDetailsList.isNotEmpty()) {
                    _productDetailsMap.apply {
                        for (productDetails in productDetailsList) {
                            put(productDetails.productId, productDetails)
                        }
                    }
                }
            }
        }
    }

    /**handle the purchase*/
    private fun handlePurchase(purchase: Purchase) {
        when (purchase.purchaseState) {
            Purchase.PurchaseState.PURCHASED -> {
                if (!purchase.isAcknowledged) {
                    val acknowledgePurchaseParams = AcknowledgePurchaseParams
                        .newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                        .build()

                    acknowledgePurchase(acknowledgePurchaseParams, purchase)
                }
            }
            Purchase.PurchaseState.PENDING -> {
                GlobalScope.launch { _subResult.emit(PurchaseResult.Pending) }
            }
            Purchase.PurchaseState.UNSPECIFIED_STATE -> {
                GlobalScope.launch { _subResult.emit(PurchaseResult.UnSpecifiedState) }
            }
        }
    }

    /**acknowledge the purchase*/
    private fun acknowledgePurchase(
        acknowledgePurchaseParams: AcknowledgePurchaseParams,
        purchase: Purchase
    ) {
        billingClient.acknowledgePurchase(
            acknowledgePurchaseParams
        ) { billingResult ->
            when (billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> runUseCase(purchase)
                BillingClient.BillingResponseCode.USER_CANCELED -> {
                    GlobalScope.launch { _subResult.emit(PurchaseResult.Cancelled) }
                }
            }
        }
    }

    /**execute the member subscribe useCase*/
    private fun runUseCase(purchase: Purchase) {
        GlobalScope.launch {
            _loading.emit(true)
            when (subscribeUseCase(purchase)) {
                is Result.Success -> {
                    _subResult.emit(PurchaseResult.Success)
                    _loading.emit(false)
                }
                is Result.Error -> {
                    _subResult.emit(PurchaseResult.Failed)
                    _loading.emit(false)
                }
            }
        }
    }

    /**stop the billing client connection*/
    fun onDestroy() {
        if (billingClient.isReady) {
            billingClient.endConnection()
        }
    }
}

