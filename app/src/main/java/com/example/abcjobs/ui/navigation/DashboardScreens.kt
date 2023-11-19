package com.example.abcjobs.ui.navigation

sealed class DashboardScreens (val route: String){
    object NewCandidate : DashboardScreens("new_candidate")
}
