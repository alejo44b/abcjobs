package com.example.abcjobs.ui.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.abcjobs.R
import com.example.abcjobs.ui.candidate.NewCandidate
import com.example.abcjobs.ui.dashboard.Home
import com.example.abcjobs.ui.login.Login
import com.example.abcjobs.ui.login.Register
import com.example.abcjobs.ui.login.SplashScreen
import com.example.abcjobs.ui.technical_tests.TechnicalTests

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

@Composable
fun DashboardNavigation(navController: NavHostController, title: MutableState<String>, img: MutableState<Int>) {
    NavHost(
        navController = navController,
        startDestination = DashboardScreens.Home.route
    ){
        composable(DashboardScreens.NewCandidate.route){
            NewCandidate(navController,title, img)
        }
        composable(DashboardScreens.TechnicalTests.route){
            val context = LocalContext.current
            title.value = context.getString(R.string.tecnical_tests)
            img.value = R.drawable.test
            TechnicalTests(title = title, img = img)
        }
        composable(DashboardScreens.Home.route){
            val context = LocalContext.current
            title.value = context.getString(R.string.layout_home)
            img.value = R.drawable.home
            Home(title = title, img = img )
        }
    }
}