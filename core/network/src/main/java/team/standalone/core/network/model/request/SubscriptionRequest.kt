package team.standalone.core.network.model.request

data class SubscriptionRequest(
    val uid: String,
    val receipt: Receipt
) {
    data class Receipt(
        val packageName: String,
        val subscriptionId: String,
        val token: String
    )

    fun toMap(): HashMap<String, Any> {
        val receiptData = hashMapOf(
            "packageName" to receipt.packageName,
            "subscriptionId" to receipt.subscriptionId,
            "token" to receipt.token
        )

        return hashMapOf(
            "receiptData" to receiptData,
            "uid" to uid
        )
    }
}