package team.standalone.core.network.model.request

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.network.util.test.SubscriptionTestUtil

class SubscriptionRequestTest: SubscriptionTestUtil() {

    private lateinit var subscriptionRequest: SubscriptionRequest
    private lateinit var subscriptionRequestHashMap: HashMap<String, Any>

    @Before
    fun setup() {
        subscriptionRequest = getTestSubscriptionRequest()
        subscriptionRequestHashMap = subscriptionRequest.toMap()
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToUid() {
        Truth.assertThat(subscriptionRequest.uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToUid() {
        val actual = "abc123"
        Truth.assertThat(subscriptionRequest.uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of packageName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToReceiptPackageName() {
        Truth.assertThat(subscriptionRequest.receipt.packageName).isEqualTo(expectedPackageName)
    }

    /**
     * Test case: should not match the value of packageName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToReceiptPackageName() {
        val actual = "fumiya.com"
        Truth.assertThat(subscriptionRequest.receipt.packageName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of subscriptionId to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToReceiptSubscriptionId() {
        Truth.assertThat(subscriptionRequest.receipt.subscriptionId).isEqualTo(expectedSubscriptionId)
    }

    /**
     * Test case: should not match the value of subscriptionId to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToReceiptSubscriptionId() {
        val actual = "321"
        Truth.assertThat(subscriptionRequest.receipt.subscriptionId).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of token to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToReceiptToken() {
        Truth.assertThat(subscriptionRequest.receipt.token).isEqualTo(expectedToken)
    }

    /**
     * Test case: should not match the value of token to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToReceiptToken() {
        val actual = "321"
        Truth.assertThat(subscriptionRequest.receipt.token).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToHashMapUid() {
        Truth
            .assertThat(subscriptionRequestHashMap["uid"])
            .isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToHashMapUid() {
        val actual = "abc123"
        Truth
            .assertThat(subscriptionRequestHashMap["uid"])
            .isNotEqualTo(actual)
    }
}