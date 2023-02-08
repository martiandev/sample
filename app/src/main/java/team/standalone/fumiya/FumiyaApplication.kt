package team.standalone.fumiya

import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import team.standalone.core.common.util.Lumberjack
import team.standalone.core.ui.androidcomponent.BaseApplication
import team.standalone.fumiya.BuildConfig.CONFIG_ONE_SIGNAL

@HiltAndroidApp
class FumiyaApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        Lumberjack.plant(BuildConfig.DEBUG)

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(CONFIG_ONE_SIGNAL)
    }
}