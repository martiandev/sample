package team.standalone.core.ui.manager

import android.net.Uri

object DeepLinkManager {

    fun resetPassword(): Uri {
        return Uri.parse("https://www.fumiya.com/reset-password")
    }

    fun termsOfService(): Uri {
        return Uri.parse("https://www.fumiya.com/terms-of-service")
    }

    fun privacyPolicy(title: String): Uri {
        return Uri.parse("https://www.fumiya.com/privacy-policy?title=$title")
    }
}
