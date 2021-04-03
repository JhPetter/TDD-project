package com.petter.testapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.petter.testapplication.presentation.ui.MainActivity
import com.petter.util.ntnChildOf
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlaylistFeature {

    val mActivityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayScreenTitle() {
        assertDisplayed("Playlist")
    }

    @Test
    fun displaysListOfPlaylists() {
        Thread.sleep(4000)

        assertRecyclerViewItemCount(R.id.playlistItems, 10)

        onView(
            allOf(
                withId(R.id.playlistName),
                isDescendantOfA(ntnChildOf(withId(R.id.playlistItems), 0))
            )
        )
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.playlistCategory),
                isDescendantOfA(ntnChildOf(withId(R.id.playlistItems), 0))
            )
        )
            .check(matches(withText("rock")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.playlistImage),
                isDescendantOfA(ntnChildOf(withId(R.id.playlistItems), 0))
            )
        )
            .check(matches(withDrawable(R.drawable.playlist)))
            .check(matches(isDisplayed()))
    }
}