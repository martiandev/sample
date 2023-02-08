package team.standalone.core.network.util.test

import team.standalone.core.network.model.request.SubscriptionRequest

open class SubscriptionTestUtil {

    var expectedUid: String = "123abc"
    val expectedPackageName: String = "com.fumiya"
    val expectedSubscriptionId: String = "123"
    val expectedToken: String = "123"

    fun getTestSubscriptionRequest(): SubscriptionRequest {
        return SubscriptionRequest(
            uid = expectedUid,
            receipt = SubscriptionRequest.Receipt(
                packageName = expectedPackageName,
                subscriptionId = expectedSubscriptionId,
                token = expectedToken
            )
        )
    }
}