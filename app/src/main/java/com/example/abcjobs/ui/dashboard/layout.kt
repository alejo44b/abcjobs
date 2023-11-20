package com.example.abcjobs.ui.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.abcjobs.R
import com.example.abcjobs.ui.navigation.DashboardNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Layout() {
    val (drawerOpen, setDrawerOpen) = remember { mutableStateOf(false) }
    val navController = rememberNavController()
    val context = LocalContext.current

    val title = remember { mutableStateOf(context.getString(R.string.layout_home)) }
    val img = remember { mutableStateOf(R.drawable.home) }
    
    if (drawerOpen) {
        DrawerMenu(onDismissRequest = { setDrawerOpen(false) }, drawerOpen = true, navController = navController)
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
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(it)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                        .padding(13.dp)
                        .shadow(2.dp, RoundedCornerShape(13.dp))
                        .clip(RoundedCornerShape(13.dp))
                        .background(color = MaterialTheme.colorScheme.surface)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(13.dp)
                        )
                        .padding(16.dp),
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Image(
                            painter = painterResource(id = img.value),
                            contentDescription = "Home",
                            modifier = Modifier
                                .size(30.dp)
                        )
                        Text(
                            text = title.value,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Divider(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        thickness = 1.dp,
                        modifier = Modifier.padding(10.dp)
                    )
                    DashboardNavigation(navController, title, img)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                        .align(Alignment.BottomCenter)
                        .border(width = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant)
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = context.getString(R.string.layout_maestria),
                            fontSize = 12.sp,
                        )
                        Text(
                            text = context.getString(R.string.layout_grupo),
                            fontSize = 12.sp,
                        )
                    }
                }

            }
        },
    )
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Layout()
}