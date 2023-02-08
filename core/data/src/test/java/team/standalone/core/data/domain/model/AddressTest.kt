package team.standalone.core.data.domain.model

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.data.util.test.AddressTestUtil

class AddressTest: AddressTestUtil() {

    private lateinit var address: Address

    @Before
    fun setup() {
        address = getTestAddress()
    }

    /**
     * Test case: should match the value of number to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToNumber() {
        Truth.assertThat(address.number).isEqualTo(expectedNumber)
    }

    /**
     * Test case: should not match the value of number to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToNumber() {
        val actual = "321"
        Truth.assertThat(address.number).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of city to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToCity() {
        Truth.assertThat(address.city).isEqualTo(expectedCity)
    }

    /**
     * Test case: should not match the value of city to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToCity() {
        val actual = "Kyoto"
        Truth.assertThat(address.city).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of prefecture to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPrefecture() {
        Truth.assertThat(address.prefecture).isEqualTo(expectedPrefecture)
    }

    /**
     * Test case: should not match the value of prefecture to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPrefecture() {
        val actual = "Kyoto"
        Truth.assertThat(address.prefecture).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of structure to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToStructure() {
        Truth.assertThat(address.structure).isEqualTo(expectedStructure)
    }

    /**
     * Test case: should not match the value of structure to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToStructure() {
        val actual = "A2"
        Truth.assertThat(address.structure).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of postalCode to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPostalCode() {
        Truth.assertThat(address.postalCode).isEqualTo(expectedPostalCode)
    }

    /**
     * Test case: should not match the value of postalCode to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPostalCode() {
        val actual = "1001B"
        Truth.assertThat(address.postalCode).isNotEqualTo(actual)
    }
}