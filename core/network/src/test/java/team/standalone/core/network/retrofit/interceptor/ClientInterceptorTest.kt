package team.standalone.core.network.retrofit.interceptor

import com.google.common.truth.Truth
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ClientInterceptorTest {

    private lateinit var clientInterceptor: ClientInterceptor

    @Mock
    private lateinit var chain: Interceptor.Chain

    @Mock
    private lateinit var expectedResponse: Response

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        clientInterceptor = ClientInterceptor()
        val requestMock = Mockito.mock(Request::class.java)
        val requestBuilderMock = Mockito.mock(Request.Builder::class.java)
        Mockito.`when`(chain.request()).thenReturn(requestMock)
        Mockito.`when`(chain.request().newBuilder()).thenReturn(requestBuilderMock)
        Mockito
            .`when`(chain.proceed(chain.request().newBuilder().build()))
            .thenReturn(expectedResponse)
    }

    /**
     * Test case: should match the value of response to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToChainResponse() {
        Truth
            .assertThat(clientInterceptor.intercept(chain))
            .isEqualTo(expectedResponse)
    }

    /**
     * Test case: should not match the value of response to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToChainResponse() {
        val actual = Mockito.mock(Response::class.java)
        Truth
            .assertThat(clientInterceptor.intercept(chain))
            .isNotEqualTo(actual)
    }
}