package team.standalone.fumiya

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    themeResId: Int = androidx.fragment.testing.R.style.FragmentScenarioEmptyFragmentActivityTheme,
    fragmentFactory: FragmentFactory? = null,
    crossinline action: Fragment.() -> Unit = {}
){
    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    ).putExtra("androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",themeResId)

    ActivityScenario.launch<HiltTestActivity>(
        mainActivityIntent
    ).onActivity { activity ->
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = it
        }
       val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        fragment.arguments = fragmentArgs
        activity.supportFragmentManager.beginTransaction()
            .add(android.R.id.content,fragment)
            .commitNow()
        (fragment as T).action()
    }

}
//inline fun <reified T : Fragment> launchFragmentInHiltContainer(
//    fragmentArgs: Bundle? = null,
//    @StyleRes themeResId: Int = androidx.fragment.testing.R.style.FragmentScenarioEmptyFragmentActivityTheme,
//    crossinline action: Fragment.() -> Unit = {}
//) {
//    val startActivityIntent = Intent.makeMainActivity(
//        ComponentName(
//            ApplicationProvider.getApplicationContext(),
//            HiltTestActivity::class.java
//        )
//    ).putExtra(
//        "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
//        themeResId
//    )
//
//    ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
//        val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
//            Preconditions.checkNotNull(T::class.java.classLoader),
//            T::class.java.name
//        )
//        fragment.arguments = fragmentArgs
//        activity.supportFragmentManager
//            .beginTransaction()
//            .add(android.R.id.content, fragment, "")
//            .commitNow()
//        fragment.action()
//    }
//}
//
//@ExperimentalCoroutinesApi
//inline fun <reified T : Fragment> launchFragmentInHiltContainer(
//    fragmentArgs: Bundle? = null,
//    @StyleRes themeResId: Int = androidx.fragment.testing.R.style.FragmentScenarioEmptyFragmentActivityTheme,
//    factory: FragmentFactory,
//    crossinline action: Fragment.() -> Unit = {}
//) {
//    val startActivityIntent = Intent.makeMainActivity(
//        ComponentName(
//            ApplicationProvider.getApplicationContext(),
//            HiltTestActivity::class.java
//        )
//    ).putExtra(
//        "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
//        themeResId
//    )
//
//    ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
//        activity.supportFragmentManager.fragmentFactory = factory
//        val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
//            Preconditions.checkNotNull(T::class.java.classLoader),
//            T::class.java.name
//        )
//        fragment.arguments = fragmentArgs
//
//        activity.supportFragmentManager
//            .beginTransaction()
//            .add(android.R.id.content, fragment, "")
//            .commit()
//        fragment.action()
//    }

//    fun withActionIconDrawable(@DrawableRes resourceId: Int): Matcher<View?>? {
//        return object : BoundedMatcher<View?, ActionMenuItemView>(ActionMenuItemView::class.java) {
//            override fun describeTo(description: Description) {
//                description.appendText("has image drawable resource $resourceId")
//            }
//
//            override fun matchesSafely(actionMenuItemView: ActionMenuItemView): Boolean {
//                return sameBitmap(
//                    actionMenuItemView.getContext(),
//                    actionMenuItemView.getItemData().getIcon(),
//                    resourceId
//                )
//            }
//        }
//    }
//}



















