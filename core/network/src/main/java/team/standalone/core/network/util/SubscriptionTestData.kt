package team.standalone.core.network.util

import team.standalone.core.common.util.ConstantUtil.Companion.FAILURE_RECEIPT_VERIFICATION
import team.standalone.core.common.util.ConstantUtil.Companion.FIRESTORE_EXCEPTION
import team.standalone.core.common.util.ConstantUtil.Companion.SERVER_NETWORK_ERROR
import team.standalone.core.network.model.ReceiptVerificationRemote

object SubscriptionTestData {

    /**set and return test [ReceiptVerificationRemote].
     * set test [ReceiptVerificationRemote.Body], and
     * status code set to 0*/
    fun billingSuccess(): ReceiptVerificationRemote {
        return ReceiptVerificationRemote(
            statusCode = 0,
            body = setBodyReceiptVerificationRemote()
        )
    }

    /**set and return test [ReceiptVerificationRemote].
     * set test [ReceiptVerificationRemote.Body], and
     * status code set to [FAILURE_RECEIPT_VERIFICATION]*/
    fun billingFailureReceiptException(): ReceiptVerificationRemote {
        return ReceiptVerificationRemote(
            statusCode = FAILURE_RECEIPT_VERIFICATION,
            body = setBodyReceiptVerificationRemote()
        )
    }

    /**set and return test [ReceiptVerificationRemote].
     * set test [ReceiptVerificationRemote.Body], and
     * status code set to [SERVER_NETWORK_ERROR]*/
    fun billingServerNetworkException(): ReceiptVerificationRemote {
        return ReceiptVerificationRemote(
            statusCode = SERVER_NETWORK_ERROR,
            body = setBodyReceiptVerificationRemote()
        )
    }

    /**set and return test [ReceiptVerificationRemote].
     * set test [ReceiptVerificationRemote.Body], and
     * status code set to [FIRESTORE_EXCEPTION]*/
    fun billingFirestoreException(): ReceiptVerificationRemote {
        return ReceiptVerificationRemote(
            statusCode = FIRESTORE_EXCEPTION,
            body = setBodyReceiptVerificationRemote()
        )
    }

    /**set and return test [ReceiptVerificationRemote.Body]*/
    private fun setBodyReceiptVerificationRemote(): ReceiptVerificationRemote.Body {
        return ReceiptVerificationRemote.Body(
            paymentState = 1,
            purchaseType = 0,
            acknowledgementState = 1,
            kind = "androidPublisherSubscriptionPurchase",
            orderId = "GPA.3316-8653-6244-67701",
            startTimeMillis = "1662075286631",
            priceAmountMicros = "200000000",
            expiryTimeMillis = "1662075699179",
            countryCode = "PH",
            priceCurrencyCode = "PHP",
            autoRenewing = true
        )
    }
}