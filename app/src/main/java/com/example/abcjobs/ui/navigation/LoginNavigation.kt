package com.example.abcjobs.ui.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.abcjobs.ui.login.Login
import com.example.abcjobs.ui.login.Register
import com.example.abcjobs.ui.login.SplashScreen

@Composable
fun LoginNavigation(application: Application) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginScreens.SplashScreen.route
    ){
        composable(LoginScreens.SplashScreen.route){
            SplashScreen(navController, application)
        }
        composable(LoginScreens.Login.route){
            Login(navController, application)
        }
        composable(LoginScreens.Register.route){
            Register(navController)
        }
    }
}