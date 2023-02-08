package team.standalone.core.network.datasource.subscription

import team.standalone.core.common.util.Result
import team.standalone.core.network.model.ReceiptVerificationRemote
import team.standalone.core.network.model.request.SubscriptionRequest

interface SubscriptionRemoteDataSource {
    /**
     * subscribed to call androidReceiptVerification
     * @param request - subscription request
     * @return receipt verification remote
     * @exception
     * - BillingException.FailureReceiptVerificationException
     * - BillingException.ServerNetworkException
     * - BillingException.FirestoreException
     */
    suspend fun receiptVerification(request: SubscriptionRequest): Result<ReceiptVerificationRemote>
}