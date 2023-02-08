package team.standalone.core.data.domain.usecase

import android.content.Context
import androidx.core.os.ConfigurationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class GetLocaleUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    /**
     * Get locale language string code.
     * */
    operator fun invoke(): Locale {
        val default = Locale.ENGLISH
        ConfigurationCompat.getLocales(context.resources.configuration)[0]?.let { locale ->
            return when (locale) {
                Locale.JAPANESE, Locale.JAPAN -> locale
                else -> default
            }
        }
        return default
    }
}