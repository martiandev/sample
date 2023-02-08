package team.standalone.core.network.model

data class ReceiptVerificationRemote(
    val statusCode: Int,
    val body: Body
) {
    data class Body(
        val paymentState: Int,
        val purchaseType: Int,
        val acknowledgementState: Int,
        val kind: String,
        val orderId: String,
        val startTimeMillis: String,
        val priceAmountMicros: String,
        val expiryTimeMillis: String,
        val countryCode: String,
        val priceCurrencyCode: String,
        val autoRenewing: Boolean
    )
}
