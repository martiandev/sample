package team.standalone.core.data.domain.model

data class Settings(
    val language: Language,
) {

    enum class Language {
        DEFAULT,
        JAPANESE,
        ENGLISH,
        SIMPLIFIED_CHINESE,
        TRADITIONAL_CHINESE,
        KOREAN
    }
}