package com.example.abcjobs.ui.technical_tests

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.abcjobs.R
import org.junit.Rule

class TechnicalTestsTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

}