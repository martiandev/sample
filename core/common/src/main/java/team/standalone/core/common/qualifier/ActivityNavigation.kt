package team.standalone.core.common.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityNavigation(val appActivity: AppActivity)

enum class AppActivity {
    AUTH
}