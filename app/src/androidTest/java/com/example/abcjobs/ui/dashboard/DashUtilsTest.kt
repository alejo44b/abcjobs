package com.example.abcjobs.ui.dashboard

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.abcjobs.R
import com.example.abcjobs.data.models.Select
import org.junit.Rule
import org.junit.Test

class DashUtilsTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testCampo() {
        composeTestRule.setContent {
            val text = mutableStateOf(context.getString(R.string.login_asset_required))
            val labelValue = context.getString(R.string.login_asset_required)
            val painter = android.R.drawable.ic_menu_help
            val validators = arrayOf("Required")
            val password = false
            val valid = mutableStateOf(true)
            Campo(text, labelValue, painter, validators, password, valid)
        }
        composeTestRule
            .onNode(hasText(context.getString(R.string.login_asset_required)))
            .assertExists()
    }

    @Test
    fun testCampoMultilinea() {
        composeTestRule.setContent {
            val text = mutableStateOf(context.getString(R.string.login_asset_required))
            val labelValue = context.getString(R.string.login_asset_required)
            val validators = arrayOf("Required")
            val password = false
            val valid = mutableStateOf(true)
            CampoMultilinea(text, labelValue, validators, password, valid)
        }
        composeTestRule
            .onNode(hasText(context.getString(R.string.login_asset_required)))
            .assertExists()
    }

    @Test
    fun testSelect() {
        composeTestRule.setContent {
            val items = listOf("item1", "item2")
            val selectedItem = mutableStateOf("item1")
            val img = android.R.drawable.ic_menu_help
            SelectF(items, selectedItem, img)
        }
        composeTestRule
            .onNode(hasText("item1"))
            .assertExists()
    }

    @Test
    fun testSelectId() {
        composeTestRule.setContent {
            val items = listOf(Select(1, "item1"), Select(2, "item2"))
            val id = mutableStateOf(1)
            val selectedItem = mutableStateOf("item1")
            val img = android.R.drawable.ic_menu_help
            SelectId(items, id, selectedItem, img)
        }
        composeTestRule
            .onNode(hasText("item1"))
            .assertExists()
    }

    @Test
     fun testBoton() {
         composeTestRule.setContent {
             val label = "label"
             val onClick = {}
             val valid = mutableStateOf(true)
             Boton(label, onClick, valid)
         }
         composeTestRule
             .onNode(hasText("label"))
             .assertExists()
     }
}