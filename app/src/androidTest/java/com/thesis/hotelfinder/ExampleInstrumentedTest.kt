package com.thesis.hotelfinder

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/***
 * Use AndroidJUnit4ClassRunner
 * Use TestRule to create global activity scenario object
 * check for visibility test
 *
 *
 */

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.thesis.hotelfinder", appContext.packageName)
    }
}
