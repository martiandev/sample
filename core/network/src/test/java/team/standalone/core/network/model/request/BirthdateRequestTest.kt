package team.standalone.core.network.model.request

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.network.firebase.util.FirebaseProperty
import team.standalone.core.network.util.test.BirthdayTestUtil

class BirthdateRequestTest: BirthdayTestUtil() {

    private lateinit var birthdateRequest: BirthdateRequest
    private lateinit var birthdateRequestHashMap: HashMap<String, Any>

    @Before
    fun setup() {
        birthdateRequest = getTestBirthdateRequest()
        birthdateRequestHashMap = birthdateRequest.toMap()
    }

    /**
     * Test case: should match the value of day to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToDay() {
        Truth.assertThat(birthdateRequest.day).isEqualTo(expectedDay)
    }

    /**
     * Test case: should not match the value of day to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToDay() {
        val actual = 1
        Truth.assertThat(birthdateRequest.day).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of month to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMonth() {
        Truth.assertThat(birthdateRequest.month).isEqualTo(expectedMonth)
    }

    /**
     * Test case: should not match the value of month to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMonth() {
        val actual = 5
        Truth.assertThat(birthdateRequest.month).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of year to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToYear() {
        Truth.assertThat(birthdateRequest.year).isEqualTo(expectedYear)
    }

    /**
     * Test case: should not match the value of year to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToYear() {
        val actual = 1990
        Truth.assertThat(birthdateRequest.year).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of day to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToHashMapDay() {
        Truth
            .assertThat(birthdateRequestHashMap[FirebaseProperty.BIRTH_DAY])
            .isEqualTo(expectedDay)
    }

    /**
     * Test case: should not match the value of day to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToHashMapDay() {
        val actual = 1
        Truth
            .assertThat(birthdateRequestHashMap[FirebaseProperty.BIRTH_DAY])
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of month to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToHashMapMonth() {
        Truth
            .assertThat(birthdateRequestHashMap[FirebaseProperty.BIRTH_MONTH])
            .isEqualTo(expectedMonth)
    }

    /**
     * Test case: should not match the value of month to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToHashMapMonth() {
        val actual = 5
        Truth
            .assertThat(birthdateRequestHashMap[FirebaseProperty.BIRTH_MONTH])
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of year to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToHashMapYear() {
        Truth
            .assertThat(birthdateRequestHashMap[FirebaseProperty.BIRTH_YEAR])
            .isEqualTo(expectedYear)
    }

    /**
     * Test case: should not match the value of year to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToHashMapYear() {
        val actual = 1990
        Truth
            .assertThat(birthdateRequestHashMap[FirebaseProperty.BIRTH_YEAR])
            .isNotEqualTo(actual)
    }
}