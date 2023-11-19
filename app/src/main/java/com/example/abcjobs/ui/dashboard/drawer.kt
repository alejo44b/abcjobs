package com.example.abcjobs.ui.dashboard

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.abcjobs.Dashboard
import com.example.abcjobs.MainActivity
import com.example.abcjobs.R
import com.example.abcjobs.data.models.MenuItem
import java.util.Locale

@Composable
fun DrawerMenu(
    onDismissRequest: () -> Unit,
    drawerOpen: Boolean,
) {
    val animationDuration = 2000

    val alpha: Float by animateFloatAsState(
        targetValue = if (drawerOpen) 0.5f else 0f,
        animationSpec = tween(durationMillis = animationDuration),
        label = ""
    )

    val offsetX: Dp by animateDpAsState(
        targetValue = if (drawerOpen) 0.dp else 200.dp,
        animationSpec = tween(durationMillis = animationDuration),
        label = ""
    )

    val context = LocalContext.current

    Popup(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black.copy(alpha = alpha)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .fillMaxHeight()
                            .offset(x = offsetX)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Column() {
                            DrawerHeader()
                            DrawerBody(
                                items = listOf(
                                    MenuItem(
                                        id = "newCandidate",
                                        title = context.getString(R.string.menu_newCandidate),
                                        icon = Icons.Filled.AccountCircle,
                                        contentDescription = context.getString(R.string.menu_newCandidate)
                                    ),
                                    MenuItem(
                                        id = "logout",
                                        title = context.getString(R.string.menu_logout),
                                        icon = Icons.Filled.ExitToApp,
                                        contentDescription = context.getString(R.string.menu_newCandidate)
                                    )
                                ) , onItemClick = {
                                    if (it.id == "logout") {
                                        val sharedPref = context.getSharedPreferences("auth", ComponentActivity.MODE_PRIVATE)
                                        with (sharedPref.edit()) {
                                            putString("token", null)
                                            putString("username", null)
                                            putString("role", null)
                                            putString("email", null)
                                            putString("companyId", null)
                                            putString("createdAt", null)
                                            putString("expiredAt", null)
                                            putString("id", null)
                                            commit()
                                        }

                                        val intent = Intent(context, MainActivity::class.java)
                                        context.startActivity(intent)
                                    }
                                    onDismissRequest()
                                } )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable { onDismissRequest() }
                    )
                }
            }
        }
    }
}

@Composable
fun DrawerHeader(){
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("auth", ComponentActivity.MODE_PRIVATE)
    val username = sharedPref.getString("username", null)
        ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    val role = sharedPref.getString("role", null)
        ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.35f)
        .background(MaterialTheme.colorScheme.primary),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.abcjobs_logo),
                contentDescription = "ABC Jobs Logo",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp)
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.usuario_logo),
                    contentDescription = "User Logo",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(60.dp)
                        .shadow(5.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color(0xFF7BA0FF))
                        .padding(10.dp)
                )
                Column {
                    Text(
                        text = username!!,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 18.sp
                    )
                    Text(
                        text = role!!,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(start = 10.dp),
                        // letra más pequeña
                        fontSize = 15.sp
                    )
                }
            }
        }
    }

}

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItem) -> Unit
){
    LazyColumn(modifier){
        items(items){item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item)
                    }.background(MaterialTheme.colorScheme.surfaceVariant)
            ){
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(25.dp),
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = item.title,
                    style = itemTextStyle,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }

    }
}
@Preview(name = "Drawer")
@Composable
private fun PreviewDrawer() {
    DrawerMenu(onDismissRequest = { }, true)
}