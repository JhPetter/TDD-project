package com.petter.testapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.petter.testapplication.presentation.di.idleResource
import com.petter.testapplication.presentation.ui.MainActivity
import com.petter.util.ntnChildOf
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test

class PlaylistFeature : BaseUITest() {

    val mActivityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayScreenTitle() {
        assertDisplayed("Playlist")
    }

    @Test
    fun displaysListOfPlaylists() {

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
                isDescendantOfA(ntnChildOf(withId(R.id.playlistItems), 1))
            )
        )
            .check(matches(withDrawable(R.drawable.playlist)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylist() {
        IdlingRegistry.getInstance().unregister(idleResource)
        assertDisplayed(R.id.playlistLoader)
    }

    @Test
    fun hidesLoader() {
        assertNotDisplayed(R.id.playlistLoader)
    }

    @Test
    fun displaysRockImageForRockListItems() {
        onView(
            allOf(
                withId(R.id.playlistImage),
                isDescendantOfA(ntnChildOf(withId(R.id.playlistItems), 0))
            )
        )
            .check(matches(withDrawable(R.drawable.rock)))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.playlistImage),
                isDescendantOfA(ntnChildOf(withId(R.id.playlistItems), 3))
            )
        )
            .check(matches(withDrawable(R.drawable.rock)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun navigateToDetailScreen() {
        onView(
            allOf(
                withId(R.id.playlistImage),
                isDescendantOfA(ntnChildOf(withId(R.id.playlistItems), 0))
            )
        )
            .perform(click())
        assertDisplayed(R.id.playlistDetailRoot)

    }
}