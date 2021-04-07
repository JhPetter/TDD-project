package com.petter.testapplication

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.petter.testapplication.presentation.di.idleResource
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class BaseUITest {

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(idleResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idleResource)
    }
}