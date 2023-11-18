package com.example.abcjobs

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.ui.Modifier
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.fillMaxSize

import com.example.abcjobs.ui.navigation.LoginNavigation
import com.example.compose.ABCJobsTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ABCJobsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginNavigation(this.application)
                }
            }
        }
    }
    override fun attachBaseContext(newBase: Context) {
        val sharedPref = newBase.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val language = sharedPref.getString("language", "en")
        val context = updateLocale(newBase, language!!)
        super.attachBaseContext(context)
    }
}

fun updateLocale(context: Context, languageCode: String): Context {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)

    return context.createConfigurationContext(config)
}