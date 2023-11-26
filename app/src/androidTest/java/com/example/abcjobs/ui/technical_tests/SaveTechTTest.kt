package com.example.abcjobs.ui.technical_tests

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.abcjobs.R
import org.junit.Rule
import org.junit.Test

class SaveTechTTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testSaveTechnicalTest(){
        composeTestRule.setContent {
            SaveTechnicalTest(
                navController = rememberNavController(),
                title = remember { mutableStateOf("") },
                img = remember { mutableStateOf(0) },
                id = 0
            )
        }
        composeTestRule
            .onNodeWithText(context.getString(R.string.save_test_button))
            .assertExists()
    }
}