package team.standalone.core.data.domain.model.param

data class SubscriptionParam(
    val uid: String,
    val receipt: Receipt
) {
    data class Receipt(
        val packageName: String,
        val subscriptionId: String,
        val token: String
    )
}