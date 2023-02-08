package team.standalone.fumiya

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import team.standalone.fumiya.ui.MainActivity


@HiltAndroidTest
class MainActivityShould{

    val rule = HiltAndroidRule(this)
        @Rule get

    lateinit var scenario:ActivityScenario<MainActivity>
    lateinit var navController:NavController


    @Before()
    fun setUp(){
        scenario = launchActivity<MainActivity>()
        navController = mock(NavController::class.java)
        scenario.onActivity {
            Navigation.setViewNavController(it.getWindow().getDecorView().findViewById(android.R.id.content),navController)
        }
        rule.inject()
    }

    @Test()
    fun beVisible(){
        assert(scenario.state==Lifecycle.State.RESUMED)
    }


    @Test()
    fun displayAppBarWithSettingsIcon(){
        onView(withId(R.id.appBarLayout))
            .check(matches(isDisplayed()))
//            .check(matches(hasDescendant(withId(R.oc))))
    }


    @Test()
    fun displayBottomNavigationBar(){
        onView(withId(R.id.nav_view))
            .check(matches(isDisplayed()))
            .check(matches(hasDescendant(withId(R.id.navigation_home))))
            .check(matches(hasDescendant(withId(R.id.navigation_special))))
            .check(matches(hasDescendant(withId(R.id.navigation_live))))
            .check(matches(hasDescendant(withId(R.id.navigation_chat))))
    }

    @Test()
    fun displayHomeWhenHomeNavItemIsClicked(){
        onView(withId(R.id.navigation_special))
            .perform(click())
        onView(withId(R.id.navigation_home))
            .perform(click())
        onView(withId(R.id.appBarLayout))
            .check(matches(hasDescendant(withText("Home"))))
        onView(withId(R.id.homeView))
            .check(matches(isDisplayed()))
    }

    @Test()
    fun displaySpecialWhenSpecialNavItemIsClicked(){
        onView(withId(R.id.navigation_special))
            .perform(click())
        onView(withId(R.id.appBarLayout))
            .check(matches(hasDescendant(withText("Special"))))
    }

    @Test()
    fun displayLiveWhenLiveNavItemIsClicked(){
        onView(withId(R.id.navigation_live))
            .perform(click())
        onView(withId(R.id.appBarLayout))
            .check(matches(hasDescendant(withText("Live"))))
    }

    @Test()
    fun displayChatWhenChatNavItemIsClicked(){
        onView(withId(R.id.navigation_chat))
            .perform(click())
        onView(withId(R.id.appBarLayout))
            .check(matches(hasDescendant(withText("Chat"))))
    }
}

