package team.standalone.core.data.domain.model

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.data.util.test.BirthdateTestUtil

class BirthdateTest: BirthdateTestUtil() {

    private lateinit var birthdate: Birthdate

    @Before
    fun setup() {
        birthdate = getTestBirthdate()
    }

    /**
     * Test case: should match the value of day to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToDay() {
        Truth.assertThat(birthdate.day).isEqualTo(expectedDay)
    }

    /**
     * Test case: should not match the value of day to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToDay() {
        val actual = 1
        Truth.assertThat(birthdate.day).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of month to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMonth() {
        Truth.assertThat(birthdate.month).isEqualTo(expectedMonth)
    }

    /**
     * Test case: should not match the value of month to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMonth() {
        val actual = 5
        Truth.assertThat(birthdate.month).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of year to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToYear() {
        Truth.assertThat(birthdate.year).isEqualTo(expectedYear)
    }

    /**
     * Test case: should not match the value of year to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToYear() {
        val actual = 1990
        Truth.assertThat(birthdate.year).isNotEqualTo(actual)
    }
}