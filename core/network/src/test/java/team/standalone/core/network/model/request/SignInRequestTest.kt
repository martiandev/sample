package team.standalone.core.network.model.request

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.network.util.test.SignInTestUtil

class SignInRequestTest: SignInTestUtil() {

    private lateinit var signInRequest: SignInRequest

    @Before
    fun setup() {
        signInRequest = getTestSignInRequest()
    }

    /**
     * Test case: should match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToEmail() {
        Truth.assertThat(signInRequest.email).isEqualTo(expectedEmail)
    }

    /**
     * Test case: should not match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToEmail() {
        val actual = "fumiya@fumiya.com"
        Truth.assertThat(signInRequest.email).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of password to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPassword() {
        Truth.assertThat(signInRequest.password).isEqualTo(expectedPassword)
    }

    /**
     * Test case: should not match the value of password to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPassword() {
        val actual = "test1234"
        Truth.assertThat(signInRequest.password).isNotEqualTo(actual)
    }
}