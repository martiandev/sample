package team.standalone.core.network.datasource.subscription

import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import team.standalone.core.common.exception.BillingException.*
import team.standalone.core.common.extension.orFalse
import team.standalone.core.common.extension.orZero
import team.standalone.core.common.qualifier.BillingFunctionSubscribe
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.network.model.ReceiptVerificationRemote
import team.standalone.core.network.model.request.SubscriptionRequest
import javax.inject.Inject

internal class SubscriptionRemoteDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @BillingFunctionSubscribe private val receiptVerification: String,
    private val functions: FirebaseFunctions
) : SubscriptionRemoteDataSource {

    override suspend fun receiptVerification(request: SubscriptionRequest):
            Result<ReceiptVerificationRemote> = taskWithResult(ioDispatcher) {

        val res: HttpsCallableResult =
            functions.getHttpsCallable(receiptVerification).call(request.toMap()).await()

        val result: Map<String, Any>? = res.data as? Map<String, Any>
        val body: Map<String, Any>? = result?.get("body") as? Map<String, Any>

        val bodyReceiptVerificationRemote = ReceiptVerificationRemote.Body(
            paymentState = (body?.get("paymentState") as? Int).orZero(),
            purchaseType = (body?.get("purchaseType") as? Int).orZero(),
            acknowledgementState = (body?.get("acknowledgementState") as? Int).orZero(),
            kind = (body?.get("kind") as? String).orEmpty(),
            orderId = (body?.get("orderId") as? String).orEmpty(),
            startTimeMillis = (body?.get("startTimeMillis") as? String).orEmpty(),
            priceAmountMicros = (body?.get("priceAmountMicros") as? String).orEmpty(),
            expiryTimeMillis = (body?.get("expiryTimeMillis") as? String).orEmpty(),
            countryCode = (body?.get("countryCode") as? String).orEmpty(),
            priceCurrencyCode = (body?.get("priceCurrencyCode") as? String).orEmpty(),
            autoRenewing = (body?.get("autoRenewing") as? Boolean).orFalse()
        )

        val receiptVerificationRemote = ReceiptVerificationRemote(
            statusCode = (result?.get("statusCode") as? Int).orZero(),
            body = bodyReceiptVerificationRemote
        )

        when (receiptVerificationRemote.statusCode) {
            FailureReceiptVerificationException.statusCode -> {
                throw FailureReceiptVerificationException
            }
            ServerNetworkException.statusCode -> {
                throw ServerNetworkException
            }
            FirestoreException.statusCode -> {
                throw FirestoreException
            }
            else -> {
                Result.Success(receiptVerificationRemote)
            }
        }
    }
}

