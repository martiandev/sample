package team.standalone.core.network.model.response

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.network.util.test.FileApiResponseTestUtil

class FileApiResponseTest: FileApiResponseTestUtil() {

    private lateinit var fileApiResponse: FileApiResponse

    @Before
    fun setup() {
        fileApiResponse = getTestFileApiResponse()
    }

    /**
     * Test case: should match the value of status to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToStatus() {
        Truth.assertThat(fileApiResponse.status).isEqualTo(expectedStatus)
    }

    /**
     * Test case: should not match the value of status to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToStatus() {
        val actual = 2
        Truth.assertThat(fileApiResponse.status).isNotEqualTo(actual)
    }


    /**
     * Test case: should match the value of fileId to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToFileId() {
        Truth.assertThat(fileApiResponse.fileId).isEqualTo(expectedFileId)
    }

    /**
     * Test case: should not match the value of fileId to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToFileId() {
        val actual = "321"
        Truth.assertThat(fileApiResponse.fileId).isNotEqualTo(actual)
    }
}