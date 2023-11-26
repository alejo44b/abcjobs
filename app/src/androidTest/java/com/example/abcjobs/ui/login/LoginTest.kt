package com.example.abcjobs.ui.login

import android.app.Application
import android.content.Context
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.rememberNavController
import com.example.abcjobs.R
import org.junit.Rule
import org.junit.Test
class LoginTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testLogin() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            val application = Application()
            Login(navController, application)
        }
        composeTestRule
            .onNodeWithContentDescription("ABC Jobs Logo")
            .assertExists()
    }

    @Test
    fun testLoginBody() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            val application = Application()
            LoginBody(navController, application)
        }
        composeTestRule
            .onNode(hasText(context.getString(R.string.greeting)))
            .assertExists()
    }

}