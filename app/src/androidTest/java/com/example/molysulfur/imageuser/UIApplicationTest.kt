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
        Thread.sleep(500)
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