package team.standalone.core.data.domain.repository

import team.standalone.core.common.util.Result
import team.standalone.core.data.domain.model.param.SubscriptionParam
import team.standalone.core.network.model.ReceiptVerificationRemote

interface SubscriptionRepository {
    /**
     * subscribed to call androidReceiptVerification
     * @param param - subscription param
     * @return receipt verification remote
     * @exception
     * - BillingException.FailureReceiptVerificationException
     * - BillingException.ServerNetworkException
     * - BillingException.FirestoreException
     */

    suspend fun receiptVerification(param: SubscriptionParam): Result<ReceiptVerificationRemote>
}