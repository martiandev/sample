package team.standalone.core.data.domain.model

import com.google.common.truth.Truth
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import team.standalone.core.data.util.test.SubscriptionTestUtil

class SubscriptionTest: SubscriptionTestUtil() {

    private lateinit var subscription: Subscription

    @Mock
    private lateinit var instant: Instant

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        subscription = getTestSubscription()
    }

    /**
     * Test case: should match the value of subscribed to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToSubscribed() {
        MockitoAnnotations.openMocks(this)
        Truth.assertThat(subscription.subscribed).isEqualTo(expectedSubscribed)
    }

    /**
     * Test case: should not match the value of subscribed to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToSubscribed() {
        val actual = false
        Truth.assertThat(subscription.subscribed).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of paused to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPaused() {
        Truth.assertThat(subscription.paused).isEqualTo(expectedPaused)
    }

    /**
     * Test case: should not match the value of paused to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPaused() {
        val actual = false
        Truth.assertThat(subscription.paused).isNotEqualTo(actual)
    }
    
    /**
     * Test case: should match the value of hasFirstSubscription to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToHasFirstSubscription() {
        val expected = false
        Truth
            .assertThat(subscription.hasFirstSubscription())
            .isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of hasFirstSubscription to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToHasFirstSubscription() {
        val expected = false
        subscription = Subscription(
            subscribed = true,
            paused = true,
            firstBillingDate = instant,
            startDate = null,
            expireDate = null
        )
        Truth
            .assertThat(subscription.hasFirstSubscription())
            .isNotEqualTo(expected)
    }
    
    /**
     * Test case: should match the value of displayFirstBillingDate to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToDisplayFirstBillingDate() {
        val expected = null
        Truth
            .assertThat(subscription.displayFirstBillingDate())
            .isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of displayFirstBillingDate to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToDisplayFirstBillingDate() {
        val expected = null
        subscription = Subscription(
            subscribed = true,
            paused = true,
            firstBillingDate = instant,
            startDate = null,
            expireDate = null
        )
        Truth
            .assertThat(subscription.displayFirstBillingDate())
            .isNotEqualTo(expected)
    }

    /**
     * Test case: should match the value of displayExpiredDate to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToDisplayExpiredDate() {
        val expected = null
        Truth
            .assertThat(subscription.displayExpiredDate())
            .isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of displayExpiredDate to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToDisplayExpiredDate() {
        val expected = null
        subscription = Subscription(
            subscribed = true,
            paused = true,
            firstBillingDate = null,
            startDate = null,
            expireDate = instant
        )
        Truth
            .assertThat(subscription.displayExpiredDate())
            .isNotEqualTo(expected)
    }
}