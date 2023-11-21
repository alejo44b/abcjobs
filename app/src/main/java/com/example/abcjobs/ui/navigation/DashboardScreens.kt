package com.example.abcjobs.ui.navigation

sealed class DashboardScreens(val route: String) {
    object Home : DashboardScreens("home")
    object NewCandidate : DashboardScreens("new_candidate")
    object TechnicalTests : DashboardScreens("technical_tests")
    object SaveTechnicalTest : DashboardScreens("save_technical_test/{id}")
    object Interviews : DashboardScreens("interviews")
    object SaveInterview : DashboardScreens("save_interview/{id}")
}