package team.standalone.core.data.domain.model.param

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.data.util.test.BirthdateTestUtil

class BirthdateParamTest: BirthdateTestUtil() {

    private lateinit var birthdateParam: BirthdateParam

    @Before
    fun setup() {
        birthdateParam = getTestBirthdateParam()
    }

    /**
     * Test case: should match the value of day to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToDay() {
        Truth.assertThat(birthdateParam.day).isEqualTo(expectedDay)
    }

    /**
     * Test case: should not match the value of day to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToDay() {
        val actual = 1
        Truth.assertThat(birthdateParam.day).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of month to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMonth() {
        Truth.assertThat(birthdateParam.month).isEqualTo(expectedMonth)
    }

    /**
     * Test case: should not match the value of month to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMonth() {
        val actual = 5
        Truth.assertThat(birthdateParam.month).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of year to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToYear() {
        Truth.assertThat(birthdateParam.year).isEqualTo(expectedYear)
    }

    /**
     * Test case: should not match the value of year to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToYear() {
        val actual = 1990
        Truth.assertThat(birthdateParam.year).isNotEqualTo(actual)
    }
}