package com.example.abcjobs.ui.navigation

sealed class DashboardScreens (val route: String){
    object Home : DashboardScreens("home")
    object NewCandidate : DashboardScreens("new_candidate")
    object TechnicalTests : DashboardScreens("technical_tests")
}
