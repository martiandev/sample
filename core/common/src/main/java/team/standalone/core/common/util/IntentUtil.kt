package team.standalone.core.common.util

import android.content.Intent
import android.net.Uri
import timber.log.Timber

object IntentUtil {

    //Facebook package name
    const val FACEBOOK = "com.facebook.katana"
    //Instagram package name
    const val INSTAGRAM = "com.instagram.android"
    //Tiktok package name
    const val TIKTOK = "com.zhiliaoapp.musically"
    //Tiktok lite package name
    const val TIKTOK_LITE = "com.zhiliaoapp.musically.go"
    //Tiktok Asia package name
    const val TIKTOK_ASIA = "com.ss.android.ugc.trill"
    //Twitter package name
    const val TWITTER = "com.twitter.android"
    //Youtube package name
    const val YOUTUBE = "com.google.android.youtube"

    fun getBrowserIntent(url: String): Intent? {
        return try {
            Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) }
        } catch (e: Exception) {
            Lumberjack.error(e)
            null
        }
    }

    fun getGooglePlayStoreIntent(packageName: String): Intent? {
        return try {
            val uri = Uri.parse("market://details?id=${packageName}")
            Intent(Intent.ACTION_VIEW, uri)
        } catch (e: Exception) {
            Lumberjack.error(e)
            val url = "https://play.google.com/store/apps/details?id=$packageName"
            getBrowserIntent(url)
        }
    }

    fun getSmsIntent(message: String): Intent? {
        return try {
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:")
                putExtra("sms_body", message)
            }
        } catch (e: Exception) {
            Lumberjack.error(e)
            null
        }
    }

    fun getSendTextIntent(message: String): Intent? {
        return try {
            Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }
        } catch (e: Exception) {
            Lumberjack.error(e)
            null
        }
    }

    /**
     * Retrieves the intent to launch a specific Facebook Page
     * @param url - URL of the page to be opened (note: id should be provided instead of page name)
     * @return intent - Intent to launch Facebook Page
     */
    fun getFacebookIntent(
        url:String
    ):Intent? {
        return try {
            var urlSplit = url.split("/")
            var id = urlSplit[urlSplit.size-1]
            return Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("fb://profile/${id}")
            )
        } catch (e:Exception){
            return  null
        }
    }

    /**
     * Returns an intent to launch a 3rd party Application
     * @param packageName - package name of the app
     * @param url - additional data to browse to redirect app
     */
    fun getAppIntent(
        packageName: String,
        url: String
    ): Intent? {

        return try {
            val uri: Uri = Uri.parse(url)
            Intent(
                Intent.ACTION_VIEW,
                uri
            ).apply {
                setPackage(packageName)
            }
        } catch (e: Exception) {
            Lumberjack.error(e)
            null
        }
    }

    fun getGooglePlayStoreSubscriptionIntent(packageName: String, sku: String): Intent? {
        return try {
            val builder = Uri.Builder()
                .scheme("https")
                .authority("play.google.com")
                .appendPath("store")
                .appendPath("account")
                .appendPath("subscriptions")
                .appendQueryParameter("package", packageName)
                .appendQueryParameter("sku", sku)
            val uri = builder.build()
            Intent(Intent.ACTION_VIEW).apply { data = uri }
        } catch (e: Exception) {
            Lumberjack.error(e)
            null
        }
    }
}