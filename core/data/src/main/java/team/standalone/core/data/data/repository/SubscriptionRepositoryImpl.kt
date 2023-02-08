package team.standalone.core.data.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.param.SubscriptionParam
import team.standalone.core.data.domain.repository.SubscriptionRepository
import team.standalone.core.network.datasource.subscription.SubscriptionRemoteDataSource
import team.standalone.core.network.model.ReceiptVerificationRemote
import team.standalone.core.network.model.request.SubscriptionRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SubscriptionRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val subscriptionRemoteDataSource: SubscriptionRemoteDataSource
) : SubscriptionRepository {

    override suspend fun receiptVerification(param: SubscriptionParam): Result<ReceiptVerificationRemote> =
        taskWithResult(ioDispatcher) {
            val request = SubscriptionRequest(
                uid = param.uid,
                receipt = param.receipt.let {
                    SubscriptionRequest.Receipt(
                        packageName = it.packageName,
                        subscriptionId = it.subscriptionId,
                        token = it.token
                    )
                }
            )
            subscriptionRemoteDataSource.receiptVerification(request)
        }
}