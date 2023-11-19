package com.example.abcjobs.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.abcjobs.ui.navigation.LoginScreens
import com.google.accompanist.insets.imePadding

@Composable
fun Register(navController: NavController) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
    ){
        LoginHeader()
        RegisterBody(navController)
    }
}

@Composable
fun RegisterBody(navController: NavController) {
    val scrollState = rememberScrollState()
    Column (
        modifier = Modifier
            .fillMaxHeight(1f)
            .verticalScroll(scrollState)
            .padding(horizontal = 0.dp, vertical = 30.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center
    ){
        val context = LocalContext.current
        var expanded by remember { mutableStateOf(false) }

        var username = remember { mutableStateOf("") }
        var email = remember { mutableStateOf("") }
        var password = remember { mutableStateOf("") }

        var selectedItem by remember { mutableStateOf(context.getString(R.string.register_select_profile)) }
        val items = listOf(context.getString(R.string.register_candidate), context.getString(R.string.register_company))

        NormalText(context.getString(R.string.greeting))
        TitleText(context.getString(R.string.register_welcome))
        LoginCampo(username, context.getString(R.string.username), R.drawable.user)
        LoginCampo(email, context.getString(R.string.email), R.drawable.mail)
        LoginCampo(password, context.getString(R.string.password), R.drawable.padlock, password = true)
        Divider(
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .padding(16.dp)
        )
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
                    painter = painterResource(id = R.drawable.profile),
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
                            expanded = false },
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
        ButtonLogin(context.getString(R.string.register_button), {})
        NormalText(text = context.getString(R.string.register_have_account))
        LoginLink(context.getString(R.string.register_login)){
            navController.navigate(LoginScreens.Login.route)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    val nav = rememberNavController()
    Register(nav)
}