package com.example.abcjobs.ui.performance

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.abcjobs.R
import org.junit.Rule
import org.junit.Test

class PerformanceTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testPerformance(){
        composeTestRule.setContent {
            Performance(
                navController = rememberNavController(),
                title = remember { mutableStateOf("") },
                img = remember { mutableStateOf(0) }
            )
        }
        composeTestRule
            .onNodeWithText(context.getString(R.string.newCan_button))
            .assertExists()
    }
}
