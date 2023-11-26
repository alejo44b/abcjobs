package com.example.abcjobs.ui.candidate

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.abcjobs.R
import org.junit.Rule
import org.junit.Test

class NewCandidateTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
    @Test
    fun testNewCandidate_field_name() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            val title = mutableStateOf("")
            val img = mutableStateOf(0)

            NewCandidate(
                navController = navController,
                title = title,
                img = img
            )
        }
        composeTestRule
            .onNode(hasText(context.getString(R.string.newCan_name)))
            .assertExists()
        composeTestRule
            .onNode(hasText(context.getString(R.string.email)))
            .assertExists()
        composeTestRule
            .onNode(hasText(context.getString(R.string.newCan_profesion)))
            .assertExists()
        composeTestRule
            .onNode(hasText(context.getString(R.string.newCan_Resumen)))
            .assertExists()
        composeTestRule
            .onNode(hasText(context.getString(R.string.newCan_docs)))
            .assertExists()
    }
}