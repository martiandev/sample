package team.standalone.core.data.domain.model.param

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.data.util.test.AuthTestUtil

class SignInParamTest: AuthTestUtil() {

    private lateinit var signInParam: SignInParam

    @Before
    fun setup() {
        signInParam = getTestSignInParam()
    }

    /**
     * Test case: should match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToEmail() {
        Truth.assertThat(signInParam.email).isEqualTo(expectedEmail)
    }

    /**
     * Test case: should not match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToEmail() {
        val actual = "info@fumiya.com"
        Truth.assertThat(signInParam.email).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of password to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPassword() {
        Truth.assertThat(signInParam.password).isEqualTo(expectedPassword)
    }

    /**
     * Test case: should not match the value of password to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPassword() {
        val actual = "1234test"
        Truth.assertThat(signInParam.password).isNotEqualTo(actual)
    }
}