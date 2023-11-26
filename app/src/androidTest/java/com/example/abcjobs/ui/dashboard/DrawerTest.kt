package com.example.abcjobs.ui.dashboard

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.abcjobs.R
import com.example.abcjobs.data.models.MenuItem
import org.junit.Rule
import org.junit.Test

class DrawerTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

    /*
     */

    @Test
    fun testDrawerMenu() {
        composeTestRule.setContent {
            val onDismissRequest = {}
            val drawerOpen = true
            val navController = NavController(context)
            DrawerMenu(onDismissRequest, drawerOpen, navController)
        }
        composeTestRule
            .onNodeWithContentDescription("ABC Jobs Logo")
            .assertExists()
    }

    @Test
    fun testDrawerHeader() {
        composeTestRule.setContent {
            DrawerHeader()
        }
        composeTestRule
            .onNodeWithContentDescription("ABC Jobs Logo")
            .assertExists()
    }

    @Test
    fun testDrawerBody() {
        composeTestRule.setContent {
            val items = listOf(MenuItem("title", "ABC Jobs Logo", "content", Icons.Filled.Home))
            DrawerBody(items,  Modifier, TextStyle(fontSize = 18.sp)) {
                Log.d("DrawerBody", "onItemClick: ")
            }
        }

        composeTestRule
            .onNode(hasText("ABC Jobs Logo"))
            .assertExists()
    }

}