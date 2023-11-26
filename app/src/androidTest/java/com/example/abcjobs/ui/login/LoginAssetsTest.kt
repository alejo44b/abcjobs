package com.example.abcjobs.ui.login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.abcjobs.R
import org.junit.Rule
import org.junit.Test

class LoginAssetsTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testLoginHeader() {
        composeTestRule.setContent {
            LoginHeader()
        }
        composeTestRule
            .onNodeWithContentDescription("ABC Jobs Logo")
            .assertExists()
    }

    @Test
    fun testNormalText() {
        composeTestRule.setContent {
            NormalText("Test")
        }
        composeTestRule
            .onNodeWithText("Test")
            .assertExists()
    }

    @Test
    fun testTitleText() {
        composeTestRule.setContent {
            TitleText("Test")
        }
        composeTestRule
            .onNodeWithText("Test")
            .assertExists()
    }

    @Test
    fun testLoginCampo() {
        composeTestRule.setContent {
            LoginCampo(mutableStateOf(""), "Test", R.drawable.test)
        }
        composeTestRule
            .onNodeWithText("Test")
            .assertExists()
    }

    @Test
    fun testButtonLogin() {
        composeTestRule.setContent {
            ButtonLogin("Test", {}, mutableStateOf(true))
        }
        composeTestRule
            .onNodeWithText("Test")
            .assertExists()
    }

    @Test
    fun testLoginLink() {
        composeTestRule.setContent {
            LoginLink("Test", {})
        }
        composeTestRule
            .onNodeWithText("Test")
            .assertExists()
    }
}