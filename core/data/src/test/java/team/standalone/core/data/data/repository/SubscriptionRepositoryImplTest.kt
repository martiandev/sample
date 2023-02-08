package team.standalone.core.data.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import team.standalone.core.common.exception.BillingException.*
import team.standalone.core.common.util.Result
import team.standalone.core.data.domain.model.param.SubscriptionParam
import team.standalone.core.data.domain.repository.SubscriptionRepository
import team.standalone.core.data.util.FakeSubscriptionDataSource
import team.standalone.core.network.model.ReceiptVerificationRemote
import team.standalone.core.network.util.SubscriptionTestData.billingFailureReceiptException
import team.standalone.core.network.util.SubscriptionTestData.billingFirestoreException
import team.standalone.core.network.util.SubscriptionTestData.billingServerNetworkException
import team.standalone.core.network.util.SubscriptionTestData.billingSuccess

@ExperimentalCoroutinesApi
class SubscriptionRepositoryImplTest {
    private lateinit var subscriptionRepository: SubscriptionRepository
    private lateinit var subscriptionDataSource: FakeSubscriptionDataSource

    @Before
    fun setUp() {
        subscriptionDataSource = FakeSubscriptionDataSource(UnconfinedTestDispatcher())

        subscriptionRepository = SubscriptionRepositoryImpl(
            ioDispatcher = UnconfinedTestDispatcher(),
            subscriptionRemoteDataSource = subscriptionDataSource
        )
    }

    /**set test [SubscriptionParam] object*/
    private fun setSubscriptionParam(): SubscriptionParam {
        return SubscriptionParam(
            uid = "testUid", SubscriptionParam.Receipt(
                packageName = "testPackage",
                subscriptionId = "testSubscriptionId",
                token = "testToken"
            )
        )
    }

    /**
     * Test case: should return receipt verification remote from [SubscriptionRepository.receiptVerification].
     * @assertions:
     *      assertTrue: result is Result.Success<ReceiptVerificationRemote>
     *      assertEquals: data.statusCode is equals to 0
     *      assertEquals: data.body.autoRenewing is equals to true*/
    @Test
    fun shouldReturnBillingSuccess() = runBlocking {
        subscriptionDataSource.setReceiptVerificationRemote(billingSuccess())
        val result = subscriptionRepository.receiptVerification(setSubscriptionParam())

        assertTrue(result is Result.Success<ReceiptVerificationRemote>)

        val resultAsReceipt = result as Result.Success<ReceiptVerificationRemote>
        assertEquals(resultAsReceipt.data.statusCode, 0)
        assertEquals(resultAsReceipt.data.body.autoRenewing, true)
    }

    /**
     * Test case: should throw failure receipt exception from [SubscriptionRepository.receiptVerification].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to BillingException.FailureReceiptVerificationException
     *      assertEquals: result.exception.message is equals to BillingException.FailureReceiptVerificationException.message*/
    @Test
    fun shouldReturnBillingFailureReceiptException() = runBlocking {
        subscriptionDataSource.setReceiptVerificationRemote(billingFailureReceiptException())
        val result = subscriptionRepository.receiptVerification(setSubscriptionParam())

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, FailureReceiptVerificationException)
        assertEquals(resultAsError.exception.message, FailureReceiptVerificationException.message)
    }

    /**
     * Test case: should throw server network exception from [SubscriptionRepository.receiptVerification].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to BillingException.ServerNetworkException
     *      assertEquals: result.exception.message is equals to BillingException.FailureReceiptVerificationException.message*/
    @Test
    fun shouldReturnBillingServerNetworkException() = runBlocking {
        subscriptionDataSource.setReceiptVerificationRemote(billingServerNetworkException())
        val result = subscriptionRepository.receiptVerification(setSubscriptionParam())

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, ServerNetworkException)
        assertEquals(resultAsError.exception.message, ServerNetworkException.message)
    }

    /**
     * Test case: should throw server network exception from [SubscriptionRepository.receiptVerification].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to BillingException.FirestoreException
     *      assertEquals: result.exception.message is equals to BillingException.FirestoreException.message*/
    @Test
    fun shouldReturnBillingFirestoreException() = runBlocking {
        subscriptionDataSource.setReceiptVerificationRemote(billingFirestoreException())
        val result = subscriptionRepository.receiptVerification(setSubscriptionParam())

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, FirestoreException)
        assertEquals(resultAsError.exception.message, FirestoreException.message)
    }
}