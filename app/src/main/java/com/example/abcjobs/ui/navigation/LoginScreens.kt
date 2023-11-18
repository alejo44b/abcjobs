package com.example.abcjobs.ui.navigation

sealed class LoginScreens (val route: String) {
    object SplashScreen : LoginScreens("splash")
    object Login : LoginScreens("login")
    object Register : LoginScreens("register")
}