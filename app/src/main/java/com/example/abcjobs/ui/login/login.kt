package com.example.abcjobs.ui.login

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.abcjobs.R

import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.abcjobs.Dashboard
import com.example.abcjobs.services.network.Security
import com.google.accompanist.insets.imePadding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.Locale

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
    var sec = Security(application)

    Column (
        modifier = Modifier
            .fillMaxHeight(1f),
        verticalArrangement = Arrangement.Center
    ){
        val username = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }

        var error by rememberSaveable { mutableStateOf(false) }
        var clicked by rememberSaveable { mutableStateOf(false) }

        val context = LocalContext.current
        var expanded by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf(context.getString(R.string.login_select_lang)) }
        val items = listOf(context.getString(R.string.login_english), context.getString(R.string.login_spanish))

        val valid = remember { mutableStateOf(false) }

        LaunchedEffect(username.value, password.value) {
            valid.value = if (username.value.isEmpty() || password.value.isEmpty()) false else valid.value
        }

        NormalText(context.getString(R.string.greeting))
        TitleText(context.getString(R.string.login_welcome))
        LoginCampo(username, context.getString(R.string.username), R.drawable.user, validators = arrayOf("Alphanumeric", "Required"), valid = valid)
        LoginCampo(password, context.getString(R.string.password), R.drawable.padlock, password = true, valid = valid)

        if(error){
            NormalText(context.getString(R.string.login_invalid_user))
        }

        if (clicked) {
            val json = JSONObject()
                .put("username", username.value)
                .put("password", password.value)
            valid.value = false
            LaunchedEffect(Unit){
                withContext(Dispatchers.IO) {
                    try {
                        Security.getInstance(application).auth(json, context)
                        val intent = Intent(context, Dashboard::class.java)
                        context.startActivity(intent)
                    }catch (e: Exception){
                        error = true
                        Log.e("LoginLogs", "Error: ${e.message}")
                    }
                }
            }
            valid.value = true
            clicked = false
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                shape = RoundedCornerShape(13.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                onClick = { expanded = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .size(50.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.idioma),
                    contentDescription = "ABC Jobs Profile",
                    Modifier
                        .size(50.dp)
                        .padding(end = 10.dp)
                )
                Text(
                    text = selectedItem,
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    color = MaterialTheme.colorScheme.outline
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            ) {
                items.forEach{label ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            selectedItem = label
                            expanded = false
                            val languageCode = when (label) {
                                context.getString(R.string.login_english) -> "en"
                                context.getString(R.string.login_spanish) -> "es"
                                else -> "en"
                            }
                            val sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                            with (sharedPref.edit()) {
                                putString("language", languageCode)
                                apply()
                            }
                            (context as Activity).recreate()
                            (context as Activity).apply {
                                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                            } },
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                    )
                }
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .padding(16.dp)
        )

        ButtonLogin(context.getString(R.string.login_button), valid = valid,
            onClick = {
                clicked = true
            }
        )

        /*NormalText(text = context.getString(R.string.login_do_not_have_account))

        LoginLink(context.getString(R.string.login_register)) {
            navController.navigate(LoginScreens.Register.route)
        }*/
    }
}

@Preview(showBackground = true)
@Composable

fun LoginPreview() {
    val nav = rememberNavController()
    Login(nav, Application())
}