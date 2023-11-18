package com.example.abcjobs.ui.login

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.abcjobs.R

import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.abcjobs.Dashboard
import com.example.abcjobs.data.models.Token
import com.example.abcjobs.data.viewmodels.AuthViewModel
import com.example.abcjobs.services.network.Security

import com.example.abcjobs.ui.navigation.LoginScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

@Composable
fun Login(navController: NavController, application: Application) {
    Column (
        modifier = Modifier.fillMaxSize(),
    ){
        LoginHeader()
        LoginBody(navController, application)
    }
}

@Composable
fun LoginBody(navController: NavController, application: Application) {

    lateinit var viewModel: AuthViewModel
    var sec = Security(application)

    Column (
        modifier = Modifier
            .fillMaxHeight(1f),
        verticalArrangement = Arrangement.Center
    ){
        var username = remember { mutableStateOf("") }
        var password = remember { mutableStateOf("") }

        var error by rememberSaveable { mutableStateOf(false) }
        var clicked by rememberSaveable { mutableStateOf(false) }

        val context = LocalContext.current

        NormalText("Hey there,")
        TitleText("Welcome Back")
        LoginCampo(username, "Username", R.drawable.user)
        LoginCampo(password, "Password", R.drawable.padlock, password = true)
        Divider(
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .padding(16.dp)
        )

        if(error){
            NormalText("Invalid username or password")
        }

        if (clicked) {
            val json = JSONObject()
                .put("username", username.value)
                .put("password", password.value)
            LaunchedEffect(Unit){
                withContext(Dispatchers.IO) {
                    try {
                        val intent = Intent(context, Dashboard::class.java)
                        context.startActivity(intent)
                        val response: Token = Security.getInstance(application).auth(json)
                        // Dirigir a la actividad Dashboard
                        Log.d("LoginLogs", "Response:${response.token}")
                    }catch (e: Exception){
                        Log.e("LoginLogs", "Error: ${e.message}")
                    }
                }
            }
        }

        ButtonLogin("Login",
            onClick = {
                clicked = true
            }
        )

        NormalText(text = "Don't have an account?")

        LoginLink("Register") {
            navController.navigate(LoginScreens.Register.route)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    val nav = rememberNavController()
    Login(nav, Application())
}