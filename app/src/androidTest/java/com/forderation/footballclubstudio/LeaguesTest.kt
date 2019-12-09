package com.forderation.footballclubstudio

import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.forderation.footballclubstudio.R.id.*
import com.forderation.footballclubstudio.activity.LeaguesActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LeaguesTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(LeaguesActivity::class.java)

    @Test
    fun testSearchEvent() {
        runBlocking {
            delay(3000)
        }
        onView(withId(rv_leagues))
            .check(matches(isDisplayed()))
        onView(withId(rv_leagues)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                5
            )
        )
        onView(withId(rv_leagues)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click())
        )
        onView(withId(next_match_tab))
            .perform(click())
        onView(withId(search_menu))
            .check(matches(isDisplayed()))
        runBlocking { delay(2000) }
        onView(withId(search_menu))
            .perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("arsenal\n"))
        pressBack()
        pressBack()
        pressBack()
        onView(withId(rv_leagues))
            .check(matches(isDisplayed()))
        onView(withId(rv_leagues)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                1
            )
        )
        onView(withId(rv_leagues)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
        )
        onView(withId(list_team_tab))
            .perform(click())
        onView(withId(search_menu))
            .check(matches(isDisplayed()))
        runBlocking { delay(2000) }
        onView(withId(search_menu))
            .perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("chelsea\n"))
    }
}
