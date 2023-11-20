package com.example.abcjobs.ui.login


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.abcjobs.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.MutableState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.text.isDigitsOnly
import java.util.regex.Pattern

@Composable
fun LoginHeader() {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .background(color = MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.abcjobs_logo),
            contentDescription = "ABC Jobs Logo",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            )

    }
}

@Composable
fun NormalText(text: String) {
    Column (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = text,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Composable
fun TitleText(text: String) {
    Column (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = text,
            style = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginCampo(text: MutableState<String>, labelValue:String, painter: Int, validators: Array<String> = arrayOf("Required"), password: Boolean = false, valid: MutableState<Boolean> = mutableStateOf(true)) {
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var clicked by rememberSaveable { mutableStateOf(false) }

    val pattern = Pattern.compile("^[a-zA-Z0-9]*$")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = text.value,
            onValueChange = {
                text.value = it
                errorMessage = if(validators.contains("Alphanumeric") && !pattern.matcher(it).matches()) context.getString(R.string.login_asset_alfanumerico) else null
                errorMessage = if(validators.contains("Required") && it.isEmpty()) context.getString(R.string.login_asset_required) else errorMessage
                valid.value = errorMessage == null
            },
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) {
                    clicked = true
                }
                if (clicked) {
                    errorMessage = if(validators.contains("Alphanumeric") && !pattern.matcher(text.value).matches()) context.getString(R.string.login_asset_alfanumerico) else null
                    errorMessage = if(validators.contains("Required") && text.value.isEmpty()) context.getString(R.string.login_asset_required) else errorMessage
                    valid.value = errorMessage == null
                }
            },
            label = { Text(labelValue) },
            isError = errorMessage != null,
            leadingIcon = { Image(
                painter = painterResource(id = painter),
                contentDescription = "Username Icon",
                modifier = Modifier.size(25.dp)) },
            shape = RoundedCornerShape(13.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
            ),
            singleLine = true,
            visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
            supportingText = {
                errorMessage?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.error
                        )
                    )
                }
            }
        )
    }
}
@Composable
fun ButtonLogin(label: String, onClick: () -> Unit, valid: MutableState<Boolean> = mutableStateOf(true)) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            enabled = valid.value,
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(13.dp)
        ) {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun LoginLink(label: String, onClick: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.primary,
            ),
        ) {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}