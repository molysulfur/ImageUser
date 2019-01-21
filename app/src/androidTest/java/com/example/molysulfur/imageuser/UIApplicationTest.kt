package com.example.molysulfur.imageuser

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
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

    // Register your Idling Resource before any tests regarding this component
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    // Unregister your Idling Resource so it can be garbage collected and does not leak any memory
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
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
        EspressoIdlingResource.increment()
        val userItem = onView(ViewMatchers.withId(R.id.recycler_main))
        EspressoIdlingResource.decrement()
        userItem
            .check(matches(isDisplayed()))
            .perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                positionMain,
                ViewActions.click()
            )
        )

        for (position in 0 until sizeThumbnail) {
            EspressoIdlingResource.increment()
            val thumbnailItem = onView(allOf(ViewMatchers.withId(R.id.recycler_thumbnail)))
            EspressoIdlingResource.decrement()
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
