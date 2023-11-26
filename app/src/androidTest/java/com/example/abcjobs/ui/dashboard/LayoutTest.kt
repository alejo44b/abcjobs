package com.example.abcjobs.ui.dashboard

import android.content.Context
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import com.example.abcjobs.R

/*
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
 */
class LayoutTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testLayout() {
        composeTestRule.setContent {
            Layout()
        }
        composeTestRule
            .onNode(hasText(context.getString(R.string.layout_home)))
            .assertExists()
    }
}