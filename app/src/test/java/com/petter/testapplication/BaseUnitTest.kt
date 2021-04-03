package com.petter.testapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.petter.testapplication.utils.MainCoroutineScopeRule
import org.junit.Rule

open class BaseUnitTest {
    @get:Rule
    val coroutinesRule = MainCoroutineScopeRule()

    @get:Rule
    val instanceExecutorRule = InstantTaskExecutorRule()
}