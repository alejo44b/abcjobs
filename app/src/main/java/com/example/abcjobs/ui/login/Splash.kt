package com.example.abcjobs.ui.login

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import com.example.abcjobs.R

import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.abcjobs.services.network.Security
import com.example.abcjobs.ui.navigation.LoginScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(navController: NavController, application: Application){
    var progress by remember { mutableFloatStateOf(0f) }
    var textoCarga by remember { mutableStateOf ("Loading...") }

    LaunchedEffect(Unit){
        val sec = Security(application)

        textoCarga = "Checking connection with Servers..."
        withContext(Dispatchers.IO) {
            try {
                if (sec.pong()) {
                    textoCarga = "Users checked"
                    for (i in 0..100) {
                        progress = i / 100f
                        delay(10)
                    }
                }
                else
                {
                    progress = 0f
                }
            }catch (e: Exception){
                Log.e("SplashScreen", "Error: ${e.message}")
            }
        }
        navController.popBackStack()
        navController.navigate(LoginScreens.Login.route)
    }
    Splash(progress, textoCarga)
}

@Composable
fun Splash(progress: Float, textoCarga: String) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.colorPrimary)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        Image(
            painter = painterResource(id = R.drawable.abcjobs_logo),
            contentDescription = "ABC Jobs Logo",
            Modifier
                .fillMaxWidth()
                .padding(50.dp)
        )
        LinearProgressIndicator(progress = progress, modifier = Modifier.padding(horizontal = 16.dp))
        NormalText(text = textoCarga)
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    Splash(0.5f, "Loading...")
}
