package com.example.molysulfur.imageuser

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UIApplicationTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        Thread.sleep(500)
        testClickRecyclerView(0, 10)
        testClickRecyclerView(1, 8)
        testClickRecyclerView(2, 12)
        testClickRecyclerView(3, 10)
        testClickRecyclerView(4, 11)
        testClickRecyclerView(5, 8)
        testClickRecyclerView(6, 9)
        testClickRecyclerView(7, 8)
        testClickRecyclerView(0, 10)
        testClickRecyclerView(1, 8)
        testClickRecyclerView(2, 12)
        testClickRecyclerView(3, 10)
        testClickRecyclerView(4, 11)
        testClickRecyclerView(5, 8)
        testClickRecyclerView(6, 9)
        testClickRecyclerView(7, 8)
    }

    private fun testClickRecyclerView(positionMain: Int, sizeThumbnail: Int) {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_main)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                positionMain,
                ViewActions.click()
            )
        )
        Thread.sleep(400)
        for (position in 0 until sizeThumbnail) {
            Espresso.onView(ViewMatchers.withId(R.id.recycler_thumbnail)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    position,
                    ViewActions.click()
                )
            )
            Thread.sleep(300)
        }
        Espresso.pressBack()
    }
}