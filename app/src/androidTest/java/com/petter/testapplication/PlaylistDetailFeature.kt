package com.petter.testapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.petter.testapplication.presentation.di.idleResource
import com.petter.testapplication.presentation.ui.MainActivity
import com.petter.util.ntnChildOf
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test

class PlaylistDetailFeature : BaseUITest() {

    val mActivityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayPlaylistNameAndDetails() {
        makePreviousSteps()

        assertDisplayed(R.id.playlistDetailName)
        assertDisplayed(R.id.playlistDetailDescription)

        assertDisplayed("Hard Rock Cafe")
        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")
    }

    @Test
    fun displayLoaderWhileFetchingDetail() {
        makePreviousSteps()
        IdlingRegistry.getInstance().unregister(idleResource)
        assertDisplayed(R.id.playlistDetailLoader)
    }

    @Test
    fun hideLoader() {
        makePreviousSteps()
        assertNotDisplayed(R.id.playlistDetailLoader)
    }

    private fun makePreviousSteps() {
        onView(
            allOf(
                withId(R.id.playlistImage),
                isDescendantOfA(ntnChildOf(withId(R.id.playlistItems), 0))
            )
        )
            .perform(ViewActions.click())
    }
}