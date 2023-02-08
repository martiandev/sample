package team.standalone.core.network.util.test

import team.standalone.core.network.model.ReceiptVerificationRemote

open class ReceiptVerificationTestUtil {

    val expectedStatusCode: Int = 1
    val expectedPaymentState: Int = 1
    val expectedPurchaseType: Int = 1
    val expectedAcknowledgementState: Int = 1
    val expectedKind: String = "kind@123"
    val expectedOrderId: String = "order@123"
    val expectedStartTimeMillis: String = "1666340050000"
    val expectedPriceAmountMicros: String = "1666340050000"
    val expectedExpiryTimeMillis: String = "1666340050000"
    val expectedCountryCode: String = "JA"
    val expectedPriceCurrencyCode: String = "JA"
    val expectedAutoRenewing: Boolean = true

    fun getTestReceiptVerificationRemote(): ReceiptVerificationRemote {
        return ReceiptVerificationRemote(
            statusCode = expectedStatusCode,
            body = ReceiptVerificationRemote.Body(
                paymentState = expectedPaymentState,
                purchaseType = expectedPurchaseType,
                acknowledgementState = expectedAcknowledgementState,
                kind = expectedKind,
                orderId = expectedOrderId,
                startTimeMillis = expectedStartTimeMillis,
                priceAmountMicros = expectedPriceAmountMicros,
                expiryTimeMillis = expectedExpiryTimeMillis,
                countryCode = expectedCountryCode,
                priceCurrencyCode = expectedPriceCurrencyCode,
                autoRenewing = expectedAutoRenewing
            )
        )
    }
}