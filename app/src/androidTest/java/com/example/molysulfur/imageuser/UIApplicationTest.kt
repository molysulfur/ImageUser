package com.example.molysulfur.imageuser

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.molysulfur.imageuser.ui.MainActivity
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UIApplicationTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(mActivityTestRule.activity.getIdlingResourceInTest())
    }

    @After
    fun unset() {
        IdlingRegistry.getInstance().unregister(mActivityTestRule.activity.getIdlingResourceInTest())
    }

    @Test
    fun mainActivityTest() {
        testClickItemInsideRecycler(0, 10)
        testClickItemInsideRecycler(1, 8)
        testClickItemInsideRecycler(2, 12)
        testClickItemInsideRecycler(3, 10)
        testClickItemInsideRecycler(4, 11)
        testClickItemInsideRecycler(5, 8)
        testClickItemInsideRecycler(6, 9)
        testClickItemInsideRecycler(7, 8)
        testClickItemInsideRecycler(0, 10)
        testClickItemInsideRecycler(1, 8)
        testClickItemInsideRecycler(2, 12)
        testClickItemInsideRecycler(3, 10)
        testClickItemInsideRecycler(4, 11)
        testClickItemInsideRecycler(5, 8)
        testClickItemInsideRecycler(6, 9)
        testClickItemInsideRecycler(7, 8)
    }

    private fun testClickItemInsideRecycler(positionMain: Int, sizeThumbnail: Int) {

        val userItem = onView(withId(R.id.recycler_main)).check(matches(isDisplayed()))
        userItem.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                positionMain,
                ViewActions.click()
            )
        )
        for (position in 0 until sizeThumbnail) {
            val thumbnailItem = onView(allOf(withId(R.id.recycler_thumbnail)))
            thumbnailItem
                .check(matches(isDisplayed()))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        position,
                        ViewActions.click()
                    )
                )
        }
        pressBack()

    }
}
