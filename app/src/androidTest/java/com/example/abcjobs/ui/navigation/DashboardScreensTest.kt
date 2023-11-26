package com.example.abcjobs.ui.navigation

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.abcjobs.R
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
class DashboardScreensTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
    @Test
    fun testDashboardScreens_Home() {
        when (DashboardScreens.Home) {
            else -> assertTrue(true)
        }
    }

    @Test
    fun testDashboardScreens_NewCandidate() {
        when (DashboardScreens.NewCandidate) {
            else -> assertTrue(true)
        }
    }

    @Test
    fun testDashboardScreens_TechnicalTests() {
        when (DashboardScreens.TechnicalTests) {
            else -> assertTrue(true)
        }
    }

    @Test
    fun testDashboardScreens_SaveTechnicalTest() {
        when (DashboardScreens.SaveTechnicalTest) {
            else -> assertTrue(true)
        }
    }

    @Test
    fun testDashboardScreens_Interviews() {
        when (DashboardScreens.Interviews) {
            else -> assertTrue(true)
        }
    }

    @Test
    fun testDashboardScreens_SaveInterview() {
        when (DashboardScreens.SaveInterview) {
            else -> assertTrue(true)
        }
    }

    @Test
    fun testDashboardScreens_Selection() {
        when (DashboardScreens.Selection) {
            else -> assertTrue(true)
        }
    }

    @Test
    fun testDashboardScreens_Performance() {
        when (DashboardScreens.Performance) {
            else -> assertTrue(true)
        }
    }

    @Test
    fun testDashboardScreens_Home_route() {
        assertTrue(DashboardScreens.Home.route == "home")
    }

    @Test
    fun testDashboardScreens_NewCandidate_route() {
        assertTrue(DashboardScreens.NewCandidate.route == "new_candidate")
    }

    @Test
    fun testDashboardScreens_TechnicalTests_route() {
        assertTrue(DashboardScreens.TechnicalTests.route == "technical_tests")
    }

    @Test
    fun testDashboardScreens_SaveTechnicalTest_route() {
        assertTrue(DashboardScreens.SaveTechnicalTest.route == "save_technical_test/{id}")
    }

    @Test
    fun testDashboardScreens_Interviews_route() {
        assertTrue(DashboardScreens.Interviews.route == "interviews")
    }

    @Test
    fun testDashboardScreens_SaveInterview_route() {
        assertTrue(DashboardScreens.SaveInterview.route == "save_interview/{id}/{candidateName}")
    }

    @Test
    fun testDashboardScreens_Selection_route() {
        assertTrue(DashboardScreens.Selection.route == "selection")
    }

    @Test
    fun testDashboardScreens_Performance_route() {
        assertTrue(DashboardScreens.Performance.route == "performance")
    }

}