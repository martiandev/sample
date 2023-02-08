package team.standalone.core.common.exception

import team.standalone.core.common.util.ConstantUtil.Companion.FAILURE_RECEIPT_VERIFICATION
import team.standalone.core.common.util.ConstantUtil.Companion.FIRESTORE_EXCEPTION
import team.standalone.core.common.util.ConstantUtil.Companion.SERVER_NETWORK_ERROR

sealed class BillingException(val statusCode: Int, message: String) : Exception(message) {
    object FailureReceiptVerificationException :
        BillingException(FAILURE_RECEIPT_VERIFICATION, "failure receipt verification")

    object ServerNetworkException : BillingException(SERVER_NETWORK_ERROR, "server network error")
    object FirestoreException : BillingException(FIRESTORE_EXCEPTION, "firestore error")
}
