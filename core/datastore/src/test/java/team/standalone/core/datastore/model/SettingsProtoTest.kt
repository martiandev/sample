package team.standalone.core.datastore.model

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class SettingsProtoTest {

    private lateinit var settingsProto: SettingsProto

    private val expected: String = "ENGLISH"

    @Before
    fun setup() {
        settingsProto = SettingsProto(expected)
    }

    /**
     * Test case: should match the value of chatLanguage to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToChatLanguage() {
        Truth.assertThat(settingsProto.chatLanguage).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of chatLanguage to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToChatLanguage() {
        val actual = "JAPANESE"
        Truth.assertThat(settingsProto.chatLanguage).isNotEqualTo(actual)
    }
}