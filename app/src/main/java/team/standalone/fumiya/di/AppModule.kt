package team.standalone.fumiya.di

import android.app.Application
import android.content.Intent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.standalone.core.common.qualifier.*
import team.standalone.core.common.qualifier.socialmedia.Facebook
import team.standalone.core.common.qualifier.socialmedia.Instragram
import team.standalone.core.common.qualifier.socialmedia.Tiktok
import team.standalone.core.common.qualifier.socialmedia.Twitter
import team.standalone.fumiya.BuildConfig
import team.standalone.fumiya.ui.auth.signin.AuthActivity

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @AppId
    @Provides
    fun providesAppId(): String = BuildConfig.APPLICATION_ID

    @AppVersionName
    @Provides
    fun providesAppVersionName(): String = BuildConfig.VERSION_NAME

    @AppVersionCode
    @Provides
    fun providesAppVersionCode(): String = BuildConfig.VERSION_CODE.toString()

    @AppFileProvider
    @Provides
    fun providesAppFileProvider(): String = "${BuildConfig.APPLICATION_ID}.fileprovider"

    @Photo
    @Provides
    fun providesPhoto(): String = BuildConfig.WEBLINK_PHOTO

    @Video
    @Provides
    fun providesVideo(): String = BuildConfig.WEBLINK_VIDEO

    @Chat
    @Provides
    fun providesChat(): String = BuildConfig.WEBLINK_CHAT

    @TransactionLaw
    @Provides
    fun providesTransactionLaw(): String = BuildConfig.WEBLINK_TRANSACTION_LAW

    @BillingItemSubscribe
    @Provides
    fun providesBillingItemSubs(): String = BuildConfig.CONFIG_BILLING_SUB_ITEM

    @BillingFunctionSubscribe
    @Provides
    fun providesBillingReceiptVerification(): String =
        BuildConfig.CONFIG_BILLING_RECEIPT_VERIFICATION

    @EmailVerification
    @Provides
    fun providesEmailVerification(): String {
        return "${BuildConfig.WEBLINK_FUMIYA_PAGE}/verify"
    }

    @UserAgent
    @Provides
    fun providesUserAgent(): String =
        " ${BuildConfig.APPLICATION_ID}/${BuildConfig.VERSION_CODE} device/android"

    @Platform
    @Provides
    fun providesPlatform(): String = "Android"

    /**
     * Endpoint for non-artist users to watch live streams
     */
    @LiveStreamWeb
    @Provides
    fun providesLiveStreamWebEndPoint(): String = BuildConfig.WEBLINK_LIVE

    @Home
    @Provides
    fun providesHome(): String = BuildConfig.WEBLINK_HOME


    @TimeoutConnect
    @Provides
    fun providesTimeoutConnect(): Long = 60

    @TimeoutRead
    @Provides
    fun providesTimeoutRead(): Long = 120

    @Facebook
    @Provides
    fun providesFacebook(): String = BuildConfig.SOCIAL_MOBILE_FACEBOOK

    @Instragram
    @Provides
    fun providesInstagram(): String = BuildConfig.SOCIAL_INSTAGRAM

    @Tiktok
    @Provides
    fun providesTiktok(): String = BuildConfig.SOCIAL_TIKTOK

    @Twitter
    @Provides
    fun providesTwitter(): String = BuildConfig.SOCIAL_TWITTER


    @Youtube
    @Provides
    fun providesYoutube(): String = BuildConfig.SOCIAL_YOUTUBE

    @YoutubeMobile
    @Provides
    fun providesYoutubeMobile(): String = BuildConfig.SOCIAL_MOBILE_YOUTUBE

    @FAQ
    @Provides
    fun providesFAQ(): String = BuildConfig.WEBLINK_FAQ

    @Inquiry
    @Provides
    fun providesInquiry(): String = BuildConfig.WEBLINK_INQUIRY

    @ActivityNavigation(AppActivity.AUTH)
    @Provides
    fun providesAuthActivity(application: Application): Intent =
        Intent(application.applicationContext, AuthActivity::class.java)

    @StandAloneLocal
    @Provides
    fun providesLocalWeb(): String = BuildConfig.WEBLINK_STANDALONE_LOCAL
}