package team.standalone.core.data.domain.model

import com.google.common.truth.Truth
import org.junit.Test

class SettingsTest {

    private lateinit var settings: Settings

    /**
     * Test case: should match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToLanguage() {
        val expected = Settings.Language.DEFAULT
        settings = Settings(language = expected)
        Truth.assertThat(settings.language).isEqualTo(expected)
    }

    /**
     * Test case: should match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToLanguageEnglish() {
        val expected = Settings.Language.ENGLISH
        settings = Settings(language = expected)
        Truth.assertThat(settings.language).isEqualTo(expected)
    }

    /**
     * Test case: should match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToLanguageJapanese() {
        val expected = Settings.Language.JAPANESE
        settings = Settings(language = expected)
        Truth.assertThat(settings.language).isEqualTo(expected)
    }

    /**
     * Test case: should match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToLanguageSimplifiedChinese() {
        val expected = Settings.Language.SIMPLIFIED_CHINESE
        settings = Settings(language = expected)
        Truth.assertThat(settings.language).isEqualTo(expected)
    }

    /**
     * Test case: should match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToLanguageTraditionalChinese() {
        val expected = Settings.Language.TRADITIONAL_CHINESE
        settings = Settings(language = expected)
        Truth.assertThat(settings.language).isEqualTo(expected)
    }
    
    /**
     * Test case: should match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToLanguageKorean() {
        val expected = Settings.Language.KOREAN
        settings = Settings(language = expected)
        Truth.assertThat(settings.language).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToLanguage() {
        val expected = Settings.Language.ENGLISH
        val actual = Settings.Language.JAPANESE
        settings = Settings(language = expected)
        Truth.assertThat(settings.language).isNotEqualTo(actual)
    }
}