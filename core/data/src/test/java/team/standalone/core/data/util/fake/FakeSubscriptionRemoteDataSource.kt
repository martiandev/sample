package team.standalone.core.data.util.fake

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.exception.BillingException.*
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.network.datasource.subscription.SubscriptionRemoteDataSource
import team.standalone.core.network.model.ReceiptVerificationRemote
import team.standalone.core.network.model.request.SubscriptionRequest
import javax.inject.Inject

class FakeSubscriptionRemoteDataSource @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SubscriptionRemoteDataSource {

    private lateinit var receiptVerificationRemote: ReceiptVerificationRemote

    /**set ReceiptVerificationRemote object*/
    fun setReceiptVerificationRemote(receipt: ReceiptVerificationRemote) {
        this.receiptVerificationRemote = receipt
    }

    override suspend fun receiptVerification(
        request: SubscriptionRequest
    ): Result<ReceiptVerificationRemote> = taskWithResult(ioDispatcher) {

        try {
            when (receiptVerificationRemote.statusCode) {
                FailureReceiptVerificationException.statusCode -> throw FailureReceiptVerificationException
                ServerNetworkException.statusCode -> throw ServerNetworkException
                FirestoreException.statusCode -> throw FirestoreException
                else -> Result.Success(receiptVerificationRemote)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
