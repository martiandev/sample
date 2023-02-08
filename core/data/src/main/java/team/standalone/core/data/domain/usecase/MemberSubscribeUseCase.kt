package team.standalone.core.data.domain.usecase

import com.android.billingclient.api.Purchase
import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.AppId
import team.standalone.core.common.qualifier.BillingItemSubscribe
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.data.domain.model.param.SubscriptionParam
import team.standalone.core.data.domain.model.param.SubscriptionParam.Receipt
import team.standalone.core.data.domain.repository.SubscriptionRepository
import team.standalone.core.data.domain.repository.UserRepository
import team.standalone.core.network.model.ReceiptVerificationRemote
import javax.inject.Inject

class MemberSubscribeUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @AppId private val appId: String,
    @BillingItemSubscribe private val billingItemSubscribe: String,
    private val userRepository: UserRepository,
    private val subscriptionRepository: SubscriptionRepository
) {
    suspend operator fun invoke(
        purchase: Purchase
    ): Result<ReceiptVerificationRemote> = taskWithResult(ioDispatcher) {

        val uid = when (val res = userRepository.getUserUid()) {
            is Result.Success -> res.data
            is Result.Error -> ""
        }

        val subscriptionParam = SubscriptionParam(
            uid = uid,
            receipt = Receipt(
                packageName = appId,
                subscriptionId = billingItemSubscribe,
                token = purchase.purchaseToken
            )
        )

        subscriptionRepository.receiptVerification(subscriptionParam)
    }
}