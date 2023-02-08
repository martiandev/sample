package team.standalone.core.network.retrofit.authenticator

import com.google.common.truth.Truth
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TokenAuthenticatorTest {

    private lateinit var tokenAuthenticator: TokenAuthenticator

    @Mock
    private lateinit var route: Route

    @Mock
    private lateinit var response: Response

    private val expectedRequest: Request? = null

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        tokenAuthenticator = TokenAuthenticator()
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToExpectedRequest() {
        Truth
            .assertThat(tokenAuthenticator.authenticate(route, response))
            .isEqualTo(expectedRequest)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToExpectedRequest() {
        val actual = Mockito.mock(Request::class.java)
        Truth
            .assertThat(tokenAuthenticator.authenticate(route, response))
            .isNotEqualTo(actual)
    }
}