package team.standalone.core.data.domain.model.param

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.data.util.test.SubscriptionTestUtil

class SubscriptionParamTest: SubscriptionTestUtil() {

    private lateinit var subscriptionParam: SubscriptionParam

    @Before
    fun setup() {
        subscriptionParam = getTestSubscriptionParam()
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToUid() {
        Truth.assertThat(subscriptionParam.uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToUid() {
        val actual = "abc123"
        Truth.assertThat(subscriptionParam.uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of packageName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToReceiptPackageName() {
        Truth.assertThat(subscriptionParam.receipt.packageName).isEqualTo(expectedPackageName)
    }

    /**
     * Test case: should not match the value of packageName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToReceiptPackageName() {
        val actual = "fumiya.com"
        Truth.assertThat(subscriptionParam.receipt.packageName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of subscriptionId to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToReceiptSubscriptionId() {
        Truth.assertThat(subscriptionParam.receipt.subscriptionId).isEqualTo(expectedSubscriptionId)
    }

    /**
     * Test case: should not match the value of subscriptionId to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToReceiptSubscriptionId() {
        val actual = "321"
        Truth.assertThat(subscriptionParam.receipt.subscriptionId).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of token to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToReceiptToken() {
        Truth.assertThat(subscriptionParam.receipt.token).isEqualTo(expectedToken)
    }

    /**
     * Test case: should not match the value of token to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToReceiptToken() {
        val actual = "321"
        Truth.assertThat(subscriptionParam.receipt.token).isNotEqualTo(actual)
    }
}