package com.example.abcjobs.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.abcjobs.Greeting
import com.example.abcjobs.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Layout() {
    val (drawerOpen, setDrawerOpen) = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (drawerOpen) {
        DrawerMenu(onDismissRequest = { setDrawerOpen(false) }, drawerOpen = true)
    }

    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth(1f)
                .background(color = MaterialTheme.colorScheme.primary)) {

                IconButton(onClick = { setDrawerOpen(true) }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu", tint= MaterialTheme.colorScheme.onPrimary)
                }

                Text(
                    text = context.getString(R.string.app_name),
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                Greeting(name = "Android")
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Layout()
}