package team.standalone.core.data.data.mapper

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.data.domain.model.Settings
import team.standalone.core.datastore.model.SettingsProto

class SettingsProtoMapperTest {

    private lateinit var settingsProtoMapper: SettingsProtoMapper

    private lateinit var settingsProto: SettingsProto
    private lateinit var settings: Settings

    @Before
    fun setup() {
        settingsProtoMapper = SettingsProtoMapper()
    }

    /**
     * Test case: should match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainEnglish() {
        settingsProto = SettingsProto(chatLanguage = "ENGLISH")
        settings = settingsProtoMapper.mapToDomain(settingsProto)
        Truth.assertThat(settings.language).isEqualTo(Settings.Language.ENGLISH)
    }

    /**
     * Test case: should not match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainEnglish() {
        settingsProto = SettingsProto(chatLanguage = "EN")
        settings = settingsProtoMapper.mapToDomain(settingsProto)
        Truth.assertThat(settings.language).isNotEqualTo(Settings.Language.ENGLISH)
    }

    /**
     * Test case: should match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainJapanese() {
        settingsProto = SettingsProto(chatLanguage = "JAPANESE")
        settings = settingsProtoMapper.mapToDomain(settingsProto)
        Truth.assertThat(settings.language).isEqualTo(Settings.Language.JAPANESE)
    }

    /**
     * Test case: should not match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainJapanese() {
        settingsProto = SettingsProto(chatLanguage = "JA")
        settings = settingsProtoMapper.mapToDomain(settingsProto)
        Truth.assertThat(settings.language).isNotEqualTo(Settings.Language.JAPANESE)
    }

    /**
     * Test case: should match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainSimplifiedChinese() {
        settingsProto = SettingsProto(chatLanguage = "SIMPLIFIED_CHINESE")
        settings = settingsProtoMapper.mapToDomain(settingsProto)
        Truth.assertThat(settings.language).isEqualTo(Settings.Language.SIMPLIFIED_CHINESE)
    }

    /**
     * Test case: should not match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainSimplifiedChinese() {
        settingsProto = SettingsProto(chatLanguage = "ZH")
        settings = settingsProtoMapper.mapToDomain(settingsProto)
        Truth.assertThat(settings.language).isNotEqualTo(Settings.Language.SIMPLIFIED_CHINESE)
    }

    /**
     * Test case: should match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainTraditionalChinese() {
        settingsProto = SettingsProto(chatLanguage = "TRADITIONAL_CHINESE")
        settings = settingsProtoMapper.mapToDomain(settingsProto)
        Truth.assertThat(settings.language).isEqualTo(Settings.Language.TRADITIONAL_CHINESE)
    }

    /**
     * Test case: should not match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainTraditionalChinese() {
        settingsProto = SettingsProto(chatLanguage = "ZH-TW")
        settings = settingsProtoMapper.mapToDomain(settingsProto)
        Truth.assertThat(settings.language).isNotEqualTo(Settings.Language.TRADITIONAL_CHINESE)
    }

    /**
     * Test case: should match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainKorean() {
        settingsProto = SettingsProto(chatLanguage = "KOREAN")
        settings = settingsProtoMapper.mapToDomain(settingsProto)
        Truth.assertThat(settings.language).isEqualTo(Settings.Language.KOREAN)
    }

    /**
     * Test case: should not match the value of language to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainKorean() {
        settingsProto = SettingsProto(chatLanguage = "KO")
        settings = settingsProtoMapper.mapToDomain(settingsProto)
        Truth.assertThat(settings.language).isNotEqualTo(Settings.Language.KOREAN)
    }
}