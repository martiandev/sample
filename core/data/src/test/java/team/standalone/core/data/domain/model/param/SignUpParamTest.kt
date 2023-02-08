package team.standalone.core.data.domain.model.param

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.data.util.test.AuthTestUtil

class SignUpParamTest: AuthTestUtil() {

    private lateinit var signUpParam: SignUpParam

    @Before
    fun setup() {
        signUpParam = getTestSignUpParam()
    }

    /**
     * Test case: should match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToEmail() {
        Truth.assertThat(signUpParam.email).isEqualTo(expectedEmail)
    }

    /**
     * Test case: should not match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToEmail() {
        val actual = "info@fumiya.com"
        Truth.assertThat(signUpParam.email).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of password to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPassword() {
        Truth.assertThat(signUpParam.password).isEqualTo(expectedPassword)
    }

    /**
     * Test case: should not match the value of password to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPassword() {
        val actual = "1234test"
        Truth.assertThat(signUpParam.password).isNotEqualTo(actual)
    }
}