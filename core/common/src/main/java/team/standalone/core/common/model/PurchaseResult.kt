package team.standalone.core.common.model

sealed class PurchaseResult {
    object Success : PurchaseResult()
    object Failed : PurchaseResult()
    object Cancelled : PurchaseResult()
    object AlreadyOwned : PurchaseResult()
    object Pending : PurchaseResult()
    object UnSpecifiedState : PurchaseResult()
}