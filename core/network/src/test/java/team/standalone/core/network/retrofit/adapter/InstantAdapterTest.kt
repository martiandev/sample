package team.standalone.core.network.retrofit.adapter

import com.google.common.truth.Truth
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import org.junit.Before
import org.junit.Test

class InstantAdapterTest {

    private lateinit var instantAdapter: InstantAdapter

    private lateinit var expectedInstant: Instant

    private val expectedTimestamp: String = "2022-12-25T15:00:00Z"

    @Before
    fun setup() {
        instantAdapter = InstantAdapter()
        expectedInstant = expectedTimestamp.toInstant()
    }

    /**
     * Test case: should match the value of toJson to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToResultValueOfToJson() {
        Truth
            .assertThat(instantAdapter.toJson(expectedInstant))
            .isEqualTo(expectedTimestamp)
    }

    /**
     * Test case: should not match the value of toJson to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToResultValueOfToJson() {
        val actual = "2020-05-25T15:20:00Z"
        Truth
            .assertThat(instantAdapter.toJson(expectedInstant))
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of fromJson to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToResultValueOfFromJson() {
        Truth
            .assertThat(instantAdapter.fromJson(expectedTimestamp))
            .isEqualTo(expectedInstant)
    }

    /**
     * Test case: should not match the value of fromJson to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToResultValueOfFromJson() {
        val actual = "2020-05-25T15:20:00Z".toInstant()
        Truth
            .assertThat(instantAdapter.fromJson(expectedTimestamp))
            .isNotEqualTo(actual)
    }
}