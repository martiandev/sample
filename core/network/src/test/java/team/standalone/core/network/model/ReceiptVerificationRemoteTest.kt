package team.standalone.core.network.model

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.network.util.test.ReceiptVerificationTestUtil

class ReceiptVerificationRemoteTest: ReceiptVerificationTestUtil() {

    private lateinit var receiptVerificationRemote: ReceiptVerificationRemote

    @Before
    fun setup() {
        receiptVerificationRemote = getTestReceiptVerificationRemote()
    }

    /**
     * Test case: should match the value of statusCode to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToStatusCode() {
        Truth.assertThat(receiptVerificationRemote.statusCode).isEqualTo(expectedStatusCode)
    }

    /**
     * Test case: should not match the value of statusCode to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToStatusCode() {
        val actual = 2
        Truth.assertThat(receiptVerificationRemote.statusCode).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of paymentState to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPaymentState() {
        Truth
            .assertThat(receiptVerificationRemote.body.paymentState)
            .isEqualTo(expectedPaymentState)
    }

    /**
     * Test case: should not match the value of paymentState to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPaymentState() {
        val actual = 2
        Truth
            .assertThat(receiptVerificationRemote.body.paymentState)
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of purchaseType to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPurchaseType() {
        Truth
            .assertThat(receiptVerificationRemote.body.purchaseType)
            .isEqualTo(expectedPurchaseType)
    }

    /**
     * Test case: should not match the value of purchaseType to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPurchaseType() {
        val actual = 2
        Truth
            .assertThat(receiptVerificationRemote.body.purchaseType)
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of acknowledgementState to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToAcknowledgementState() {
        Truth
            .assertThat(receiptVerificationRemote.body.acknowledgementState)
            .isEqualTo(expectedAcknowledgementState)
    }

    /**
     * Test case: should not match the value of acknowledgementState to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToAcknowledgementState() {
        val actual = 2
        Truth
            .assertThat(receiptVerificationRemote.body.acknowledgementState)
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of kind to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToKind() {
        Truth
            .assertThat(receiptVerificationRemote.body.kind)
            .isEqualTo(expectedKind)
    }

    /**
     * Test case: should not match the value of kind to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToKind() {
        val actual = "kind@abc"
        Truth
            .assertThat(receiptVerificationRemote.body.kind)
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of orderId to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToOrderId() {
        Truth
            .assertThat(receiptVerificationRemote.body.orderId)
            .isEqualTo(expectedOrderId)
    }

    /**
     * Test case: should not match the value of orderId to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToOrderId() {
        val actual = "order@abc"
        Truth
            .assertThat(receiptVerificationRemote.body.orderId)
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of startTimeMillis to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToStartTimeMillis() {
        Truth
            .assertThat(receiptVerificationRemote.body.startTimeMillis)
            .isEqualTo(expectedStartTimeMillis)
    }

    /**
     * Test case: should not match the value of startTimeMillis to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToStartTimeMillis() {
        val actual = "1666340050099"
        Truth
            .assertThat(receiptVerificationRemote.body.startTimeMillis)
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of priceAmountMicros to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPriceAmountMicros() {
        Truth
            .assertThat(receiptVerificationRemote.body.priceAmountMicros)
            .isEqualTo(expectedPriceAmountMicros)
    }

    /**
     * Test case: should not match the value of priceAmountMicros to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPriceAmountMicros() {
        val actual = "1666340050099"
        Truth
            .assertThat(receiptVerificationRemote.body.priceAmountMicros)
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of expiryTimeMillis to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToExpiryTimeMillis() {
        Truth
            .assertThat(receiptVerificationRemote.body.expiryTimeMillis)
            .isEqualTo(expectedExpiryTimeMillis)
    }

    /**
     * Test case: should not match the value of expiryTimeMillis to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToExpiryTimeMillis() {
        val actual = "1666340050099"
        Truth
            .assertThat(receiptVerificationRemote.body.expiryTimeMillis)
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of countryCode to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToCountryCode() {
        Truth
            .assertThat(receiptVerificationRemote.body.countryCode)
            .isEqualTo(expectedCountryCode)
    }

    /**
     * Test case: should not match the value of countryCode to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToCountryCode() {
        val actual = "PH"
        Truth
            .assertThat(receiptVerificationRemote.body.countryCode)
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of priceCurrencyCode to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPriceCurrencyCode() {
        Truth
            .assertThat(receiptVerificationRemote.body.priceCurrencyCode)
            .isEqualTo(expectedPriceCurrencyCode)
    }

    /**
     * Test case: should not match the value of priceCurrencyCode to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPriceCurrencyCode() {
        val actual = "PH"
        Truth
            .assertThat(receiptVerificationRemote.body.priceCurrencyCode)
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of autoRenewing to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToAutoRenewing() {
        Truth
            .assertThat(receiptVerificationRemote.body.autoRenewing)
            .isEqualTo(expectedAutoRenewing)
    }

    /**
     * Test case: should not match the value of autoRenewing to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToAutoRenewing() {
        val actual = false
        Truth
            .assertThat(receiptVerificationRemote.body.autoRenewing)
            .isNotEqualTo(actual)
    }
}