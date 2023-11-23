package com.example.abcjobs.ui.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abcjobs.R
import com.example.abcjobs.data.models.Select
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Campo(text: MutableState<String>, labelValue:String, painter: Int, validators: Array<String> = arrayOf("Required"), password: Boolean = false, valid: MutableState<Boolean> = mutableStateOf(true)) {
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var clicked by rememberSaveable { mutableStateOf(false) }

    val pattern = Pattern.compile("^[a-zA-Z0-9]*$")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = text.value,
            onValueChange = {
                text.value = it
                errorMessage = if(validators.contains("Alphanumeric") && !pattern.matcher(it).matches()) context.getString(
                    R.string.login_asset_alfanumerico) else null
                errorMessage = if(validators.contains("Alphanumeric_es") && !Pattern.matches("^[a-zA-Z0-9 ]*$", it)) context.getString(R.string.login_asset_alfanumerico) else errorMessage
                errorMessage = if(validators.contains("Email") && !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) context.getString(
                    R.string.login_asset_email) else errorMessage
                errorMessage = if(validators.contains("Numeric") && !Pattern.matches("^[0-9]*$", it)) context.getString(R.string.login_asset_numeric) else errorMessage
                errorMessage = if(validators.contains("Required") && it.isEmpty()) context.getString(
                    R.string.login_asset_required) else errorMessage
                valid.value = errorMessage == null
            },
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) {
                        clicked = true
                    }
                    if (clicked) {
                        errorMessage = if(validators.contains("Alphanumeric") && !pattern.matcher(text.value).matches()) context.getString(R.string.login_asset_alfanumerico) else null
                        errorMessage = if(validators.contains("Alphanumeric_es") && !Pattern.matches("^[a-zA-Z0-9 ]*$", text.value)) context.getString(R.string.login_asset_alfanumerico) else errorMessage
                        errorMessage = if(validators.contains("Email") && !android.util.Patterns.EMAIL_ADDRESS.matcher(text.value).matches()) context.getString(R.string.login_asset_email) else errorMessage
                        errorMessage = if(validators.contains("Numeric") && !Pattern.matches("^[0-9]*$", text.value)) context.getString(R.string.login_asset_numeric) else errorMessage
                        errorMessage = if(validators.contains("Required") && text.value.isEmpty()) context.getString(R.string.login_asset_required) else errorMessage
                        valid.value = errorMessage == null
                    }
                }
                .fillMaxWidth(),
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
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.error
                        )
                    )
                }
            }
        )
    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoMultilinea(text: MutableState<String>, labelValue:String, validators: Array<String> = arrayOf("Required"), password: Boolean = false, valid: MutableState<Boolean> = mutableStateOf(true)) {
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var clicked by rememberSaveable { mutableStateOf(false) }

    val pattern = Pattern.compile("^[a-zA-Z0-9]*$")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = text.value,
                onValueChange = {
                    if (it.split("\\s+".toRegex()).size <= 1000) {
                        text.value = it
                    }
                    errorMessage = if(validators.contains("Alphanumeric") && !pattern.matcher(it).matches()) context.getString(
                        R.string.login_asset_alfanumerico) else null
                    errorMessage = if(validators.contains("Alphanumeric_es") && !Pattern.matches("^[a-zA-Z0-9 ]*$", it)) context.getString(R.string.login_asset_alfanumerico) else errorMessage
                    errorMessage = if(validators.contains("Email") && !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) context.getString(
                        R.string.login_asset_email) else errorMessage
                    errorMessage = if(validators.contains("Required") && it.isEmpty()) context.getString(
                        R.string.login_asset_required) else errorMessage
                    valid.value = errorMessage == null
                },
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            clicked = true
                        }
                        if (clicked) {
                            errorMessage = if(validators.contains("Alphanumeric") && !pattern.matcher(text.value).matches()) context.getString(R.string.login_asset_alfanumerico) else null
                            errorMessage = if(validators.contains("Alphanumeric_es") && !Pattern.matches("^[a-zA-Z0-9 ]*$", text.value)) context.getString(R.string.login_asset_alfanumerico) else errorMessage
                            errorMessage = if(validators.contains("Email") && !android.util.Patterns.EMAIL_ADDRESS.matcher(text.value).matches()) context.getString(R.string.login_asset_email) else errorMessage
                            errorMessage = if(validators.contains("Required") && text.value.isEmpty()) context.getString(R.string.login_asset_required) else errorMessage
                            valid.value = errorMessage == null
                        }
                    }
                    .fillMaxWidth()
                    .size(150.dp),
                label = { Text(labelValue) },
                maxLines = 5,
                isError = errorMessage != null,
                shape = RoundedCornerShape(13.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                ),
                visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
                supportingText = {
                    errorMessage?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.error
                            )
                        )
                    }
                })
    }
}

@Composable
fun Select(items: List<String>, selectedItem: MutableState<String>, img: Int) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 5.dp),
    ) {
        TextButton(
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp),
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ){
                Image(
                    painter = painterResource(id = img),
                    contentDescription = "ABC Jobs",
                    Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
                )
                Text(
                    text = selectedItem.value,
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    color = MaterialTheme.colorScheme.outline
                )
            }
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
                        selectedItem.value = label
                        expanded = false },
                    modifier = Modifier
                        .padding(horizontal = 25.dp)
                )
            }
        }
    }

}

@Composable
fun SelectId(items: List<Select>, id: MutableState<Int>, selectedItem: MutableState<String>, img: Int) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 5.dp),
    ) {
        TextButton(
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp),
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ){
                Image(
                    painter = painterResource(id = img),
                    contentDescription = "ABC Jobs",
                    Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
                )
                Text(
                    text = selectedItem.value,
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    color = MaterialTheme.colorScheme.outline
                )
            }
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
                    text = { Text(label.name) },
                    onClick = {
                        selectedItem.value = label.name
                        id.value = label.id
                        expanded = false },
                    modifier = Modifier
                        .padding(horizontal = 25.dp)
                )
            }
        }
    }

}
@Composable
fun Boton(label: String, onClick: () -> Unit, valid: MutableState<Boolean> = mutableStateOf(true)) {
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