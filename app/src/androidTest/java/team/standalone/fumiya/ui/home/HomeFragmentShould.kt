package team.standalone.fumiya.ui.home

import android.webkit.WebView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.web.assertion.WebAssertion
import androidx.test.espresso.web.assertion.WebViewAssertions
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms
import androidx.test.espresso.web.webdriver.DriverAtoms.webClick
import androidx.test.espresso.web.webdriver.Locator

import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import team.standalone.fumiya.BuildConfig
import team.standalone.fumiya.R
import team.standalone.fumiya.launchFragmentInHiltContainer

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class HomeFragmentShould{

    @get:Rule
    var rule = HiltAndroidRule(this)
    var webView: WebView? = null
    @Before
    fun setUp() {
        rule.inject()
        launchFragmentInHiltContainer<HomeFragment> {
            webView = view?.findViewById<WebView>(R.id.homeView)
        }
        onWebView().forceJavascriptEnabled()
    }

    @Test
    fun displayWebView(){
        onView(withId(R.id.homeView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displayHome(){
        Thread.sleep(4000)
        onWebView()
            .withElement(DriverAtoms.findElement(Locator.CLASS_NAME, "campaignTitle"))
            .check(
                WebViewAssertions.webMatches(
                    DriverAtoms.getText(),
                    Matchers.containsString("Special Contents")
                )
            )
    }

    @Test
    fun playVideo(){
        Thread.sleep(4000)
        onWebView()
            .withElement(DriverAtoms.findElement(Locator.CLASS_NAME,"videoLink"))
            .perform(webClick())
        Thread.sleep(4000)
        onWebView()
            .withElement(DriverAtoms.findElement(Locator.TAG_NAME,"video"))
    }



    @After
    fun tearDown(){

    }

}