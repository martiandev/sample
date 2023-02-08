package team.standalone.core.network.model.request

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.network.util.test.SignUpTestUtil

class SignUpRequestTest: SignUpTestUtil() {

    private lateinit var signUpRequest: SignUpRequest

    @Before
    fun setup() {
        signUpRequest = getTestSignUpRequest()
    }

    /**
     * Test case: should match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToEmail() {
        Truth.assertThat(signUpRequest.email).isEqualTo(expectedEmail)
    }

    /**
     * Test case: should not match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToEmail() {
        val actual = "fumiya@fumiya.com"
        Truth.assertThat(signUpRequest.email).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of password to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPassword() {
        Truth.assertThat(signUpRequest.password).isEqualTo(expectedPassword)
    }

    /**
     * Test case: should not match the value of password to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPassword() {
        val actual = "test1234"
        Truth.assertThat(signUpRequest.password).isNotEqualTo(actual)
    }
}