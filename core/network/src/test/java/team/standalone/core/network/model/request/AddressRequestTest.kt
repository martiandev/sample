package team.standalone.core.network.model.request

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.network.firebase.util.FirebaseProperty
import team.standalone.core.network.util.test.AddressTestUtil

class AddressRequestTest: AddressTestUtil() {

    private lateinit var addressRequest: AddressRequest
    private lateinit var addressRequestHashMap: HashMap<String, Any>

    @Before
    fun setup() {
        addressRequest = getTestAddressRequest()
        addressRequestHashMap = addressRequest.toMap()
    }

    /**
     * Test case: should match the value of number to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToNumber() {
        Truth.assertThat(addressRequest.number).isEqualTo(expectedNumber)
    }

    /**
     * Test case: should not match the value of number to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToNumber() {
        val actual = "321"
        Truth.assertThat(addressRequest.number).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of city to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToCity() {
        Truth.assertThat(addressRequest.city).isEqualTo(expectedCity)
    }

    /**
     * Test case: should not match the value of city to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToCity() {
        val actual = "Kyoto"
        Truth.assertThat(addressRequest.city).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of prefecture to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPrefecture() {
        Truth.assertThat(addressRequest.prefecture).isEqualTo(expectedPrefecture)
    }

    /**
     * Test case: should not match the value of prefecture to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPrefecture() {
        val actual = "Kyoto"
        Truth.assertThat(addressRequest.prefecture).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of structure to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToStructure() {
        Truth.assertThat(addressRequest.structure).isEqualTo(expectedStructure)
    }

    /**
     * Test case: should not match the value of structure to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToStructure() {
        val actual = "A2"
        Truth.assertThat(addressRequest.structure).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of postalCode to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPostalCode() {
        Truth.assertThat(addressRequest.postalCode).isEqualTo(expectedPostalCode)
    }

    /**
     * Test case: should not match the value of postalCode to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPostalCode() {
        val actual = "1001B"
        Truth.assertThat(addressRequest.postalCode).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of number to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToHashMapNumber() {
        Truth
            .assertThat(addressRequestHashMap[FirebaseProperty.ADDRESS_NUMBER])
            .isEqualTo(expectedNumber)
    }

    /**
     * Test case: should not match the value of number to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToHashMapNumber() {
        val actual = "321"
        Truth
            .assertThat(addressRequestHashMap[FirebaseProperty.ADDRESS_NUMBER])
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of city to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToHashMapCity() {
        Truth
            .assertThat(addressRequestHashMap[FirebaseProperty.ADDRESS_CITY])
            .isEqualTo(expectedCity)
    }

    /**
     * Test case: should not match the value of city to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToHashMapCity() {
        val actual = "Kyoto"
        Truth
            .assertThat(addressRequestHashMap[FirebaseProperty.ADDRESS_CITY])
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of prefecture to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToHashMapPrefecture() {
        Truth
            .assertThat(addressRequestHashMap[FirebaseProperty.ADDRESS_PREFECTURE])
            .isEqualTo(expectedPrefecture)
    }

    /**
     * Test case: should not match the value of prefecture to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToHashMapPrefecture() {
        val actual = "Kyoto"
        Truth
            .assertThat(addressRequestHashMap[FirebaseProperty.ADDRESS_PREFECTURE])
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of structure to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToHashMapStructure() {
        Truth
            .assertThat(addressRequestHashMap[FirebaseProperty.ADDRESS_STRUCTURE])
            .isEqualTo(expectedStructure)
    }

    /**
     * Test case: should not match the value of structure to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToHashMapStructure() {
        val actual = "A2"
        Truth
            .assertThat(addressRequestHashMap[FirebaseProperty.ADDRESS_STRUCTURE])
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of postalCode to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToHashMapPostalCode() {
        Truth
            .assertThat(addressRequestHashMap[FirebaseProperty.ADDRESS_POSTAL_CODE])
            .isEqualTo(expectedPostalCode)
    }

    /**
     * Test case: should not match the value of postalCode to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToHashMapPostalCode() {
        val actual = "1001B"
        Truth
            .assertThat(addressRequestHashMap[FirebaseProperty.ADDRESS_POSTAL_CODE])
            .isNotEqualTo(actual)
    }
}