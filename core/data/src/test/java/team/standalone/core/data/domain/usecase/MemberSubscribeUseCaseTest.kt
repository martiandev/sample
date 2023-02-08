package team.standalone.core.data.domain.usecase

import com.android.billingclient.api.Purchase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import team.standalone.core.common.exception.BillingException.*
import team.standalone.core.common.util.Result
import team.standalone.core.data.data.mapper.UserEntityMapper
import team.standalone.core.data.data.mapper.UserParamMapper
import team.standalone.core.data.data.mapper.UserRemoteMapper
import team.standalone.core.data.data.repository.SubscriptionRepositoryImpl
import team.standalone.core.data.data.repository.UserRepositoryImpl
import team.standalone.core.data.domain.repository.SubscriptionRepository
import team.standalone.core.data.domain.repository.UserRepository
import team.standalone.core.data.util.fake.FakeSubscriptionRemoteDataSource
import team.standalone.core.data.util.fake.FakeUserDao
import team.standalone.core.data.util.fake.FakeUserLocalDataSource
import team.standalone.core.data.util.fake.FakeUserRemoteDataSource
import team.standalone.core.network.model.ReceiptVerificationRemote
import team.standalone.core.network.util.SubscriptionTestData.billingFailureReceiptException
import team.standalone.core.network.util.SubscriptionTestData.billingFirestoreException
import team.standalone.core.network.util.SubscriptionTestData.billingServerNetworkException
import team.standalone.core.network.util.SubscriptionTestData.billingSuccess
import team.standalone.core.network.util.UserRemoteTestData
import team.standalone.core.network.util.UserRemoteTestData.signedInUser

@ExperimentalCoroutinesApi
class MemberSubscribeUseCaseTest {

    private lateinit var memberSubscribeUseCase: MemberSubscribeUseCase
    private lateinit var userRepository: UserRepository
    private lateinit var subscriptionRepository: SubscriptionRepository

    private lateinit var fakeUserRemoteDataSource: FakeUserRemoteDataSource
    private lateinit var fakeSubscriptionDataSource: FakeSubscriptionRemoteDataSource

    @MockK
    private lateinit var purchase: Purchase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        fakeUserRemoteDataSource = FakeUserRemoteDataSource(
            UnconfinedTestDispatcher(),
            UserRemoteTestData.getUserRemoteList()
        )

        userRepository = UserRepositoryImpl(
            UnconfinedTestDispatcher(), FakeUserLocalDataSource(
                UnconfinedTestDispatcher(), FakeUserDao()
            ), fakeUserRemoteDataSource, UserEntityMapper(), UserRemoteMapper(), UserParamMapper()
        )

        fakeSubscriptionDataSource = FakeSubscriptionRemoteDataSource(UnconfinedTestDispatcher())

        subscriptionRepository = SubscriptionRepositoryImpl(
            UnconfinedTestDispatcher(), fakeSubscriptionDataSource
        )

        memberSubscribeUseCase = MemberSubscribeUseCase(
            appId = "testAppId",
            billingItemSubscribe = "testBillingItemSubscribe",
            ioDispatcher = UnconfinedTestDispatcher(),
            userRepository = userRepository,
            subscriptionRepository = subscriptionRepository
        )

        /**mocked the purchaseToken from the billing client api [Purchase] object in each test cases*/
        every { purchase.purchaseToken } returns "testToken"

        /**set fake user signed in user in [FakeUserRemoteDataSource] in each test cases*/
        fakeUserRemoteDataSource.setSignedInUser(signedInUser())
    }

    /**
     * Test case: should return receipt verification remote from [MemberSubscribeUseCase] when invoked.
     * @assertions:
     *      assertTrue: result is Result.Success<ReceiptVerificationRemote>
     *      assertEquals: data.statusCode is equals to 0
     *      assertEquals: data.body.autoRenewing is equals to true*/
    @Test
    fun shouldReturnBillingSuccess() = runBlocking {
        fakeSubscriptionDataSource.setReceiptVerificationRemote(billingSuccess())

        val result = memberSubscribeUseCase.invoke(purchase)

        assertTrue(result is Result.Success<ReceiptVerificationRemote>)

        val resultAsReceipt = result as Result.Success<ReceiptVerificationRemote>
        assertEquals(resultAsReceipt.data.statusCode, 0)
        assertEquals(resultAsReceipt.data.body.autoRenewing, true)
    }

    /**
     * Test case: should throw failure receipt exception from [MemberSubscribeUseCase] when invoked.
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to BillingException.FailureReceiptVerificationException
     *      assertEquals: result.exception.message is equals to BillingException.FailureReceiptVerificationException.message*/
    @Test
    fun shouldReturnFailureReceiptException() = runBlocking {
        fakeSubscriptionDataSource.setReceiptVerificationRemote(billingFailureReceiptException())

        val result = memberSubscribeUseCase.invoke(purchase)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, FailureReceiptVerificationException)
        assertEquals(resultAsError.exception.message, FailureReceiptVerificationException.message)
    }

    /**
     * Test case: should throw server network exception from [MemberSubscribeUseCase] when invoked.
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to BillingException.ServerNetworkException
     *      assertEquals: result.exception.message is equals to BillingException.ServerNetworkException.message*/
    @Test
    fun shouldReturnServerNetworkException() = runBlocking {
        fakeSubscriptionDataSource.setReceiptVerificationRemote(billingServerNetworkException())

        val result = memberSubscribeUseCase.invoke(purchase)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, ServerNetworkException)
        assertEquals(resultAsError.exception.message, ServerNetworkException.message)
    }

    /**
     * Test case: should throw firestore exception from [MemberSubscribeUseCase] when invoked.
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to BillingException.FirestoreException
     *      assertEquals: result.exception.message is equals to BillingException.FirestoreException.message*/
    @Test
    fun shouldReturnFirestoreException() = runBlocking {
        fakeSubscriptionDataSource.setReceiptVerificationRemote(billingFirestoreException())

        val result = memberSubscribeUseCase.invoke(purchase)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, FirestoreException)
        assertEquals(resultAsError.exception.message, FirestoreException.message)
    }
}