package com.example.abcjobs.ui.interview

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test
import com.example.abcjobs.R
class InterviewsTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testInterviews() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            val title = remember { mutableStateOf("") }
            val img = remember { mutableStateOf(0) }
            Interviews(navController, title, img)
        }
        composeTestRule
            .onNode(hasText(context.getString(R.string.tests_candidate)))
            .assertExists()
    }
}