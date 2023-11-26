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
class InterviewResultTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testInterviewResult() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            val title = remember { mutableStateOf("") }
            val img = remember { mutableStateOf(0) }
            Interviews(navController, title, img)
            InterviewResult(
                navController = navController,
                title = title,
                img = img,
                id = 0,
                candidate = ""
            )
        }
        composeTestRule
            .onNode(hasText(context.getString(R.string.interview_resultado)))
            .assertExists()
    }

}