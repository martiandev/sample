package team.standalone.core.data.util.test

import team.standalone.core.data.domain.model.Subscription
import team.standalone.core.data.domain.model.param.SubscriptionParam

open class SubscriptionTestUtil {

    val expectedUid: String = "123abc"
    val expectedPackageName: String = "com.fumiya"
    val expectedSubscriptionId: String = "123"
    val expectedToken: String = "123"
    var expectedSubscribed: Boolean = true
    var expectedPaused: Boolean = true

    fun getTestSubscription(): Subscription {
        return Subscription(
            subscribed = expectedSubscribed,
            paused = expectedPaused,
            firstBillingDate = null,
            startDate = null,
            expireDate = null
        )
    }

    fun getTestSubscriptionParam(): SubscriptionParam {
        return SubscriptionParam(
            uid = expectedUid,
            receipt = SubscriptionParam.Receipt(
                packageName = expectedPackageName,
                subscriptionId = expectedSubscriptionId,
                token = expectedToken
            )
        )
    }
}