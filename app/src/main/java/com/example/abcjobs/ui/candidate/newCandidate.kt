package com.example.abcjobs.ui.candidate

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.abcjobs.R
import com.example.abcjobs.services.network.ItSpecialistsAdapter
import com.example.abcjobs.ui.dashboard.Boton
import com.example.abcjobs.ui.dashboard.Campo
import com.example.abcjobs.ui.dashboard.CampoMultilinea
import com.example.abcjobs.ui.dashboard.SelectF
import com.google.accompanist.insets.imePadding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import okhttp3.internal.wait
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

@Composable
fun NewCandidate(navController: NavController, title: MutableState<String>, img: MutableState<Int>) {
    val context = LocalContext.current
    title.value = context.getString(R.string.menu_newCandidate)
    img.value = R.drawable.usuario_logo

    val nombre = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val nacionalidad = remember { mutableStateOf(context.getString(R.string.newCan_Nacionalidad)) }
    val profesion = remember { mutableStateOf("") }
    val especialidad = remember { mutableStateOf(context.getString(R.string.newCan_Specialization)) }
    val resumen = remember { mutableStateOf("") }
    val documentos = remember { mutableStateOf("") }

    val docUri = remember { mutableStateOf<Uri?>(null) }

    var clicked by rememberSaveable { mutableStateOf(false) }
    val valid = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }

    val pickFileResult = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val cursor = context.contentResolver.query(it, null, null, null, null)
            val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            var name: String? = null
            if (cursor?.moveToFirst() == true) {
                name = nameIndex?.let { index -> cursor.getString(index) }
            }
            cursor?.close()

            documentos.value = name ?: it.toString()
            docUri.value = it
        }
    }

    LaunchedEffect(nombre.value, email.value, nacionalidad.value, profesion.value, especialidad.value, resumen.value) {
        valid.value =
            if (nombre.value.isEmpty() || email.value.isEmpty()
                || nacionalidad.value == context.getString(R.string.newCan_Nacionalidad)
                || profesion.value.isEmpty() || especialidad.value == context.getString(R.string.newCan_Specialization)
                || resumen.value.isEmpty()) false else valid.value
    }

    if (clicked) {
        val sharedPref = context.getSharedPreferences("auth", ComponentActivity.MODE_PRIVATE)
        val userId = sharedPref.getString("id", null)?.toInt()
        val token = sharedPref.getString("token", null)
        val json = JSONObject()
            .put("userId", userId)
            .put("name", nombre.value)
            .put("email", email.value)
            .put("nationality", nacionalidad.value)
            .put("profession", profesion.value)
            .put("speciality", especialidad.value)
            .put("profile", resumen.value)
        valid.value = false
        LaunchedEffect(Unit){
            withContext(Dispatchers.IO) {
                try {
                    val filePath = getPathFromUri(context, docUri.value!!, documentos.value)
                    val file = File(filePath)
                    if (file.exists()) {
                        if (ItSpecialistsAdapter.getInstance(context).createItSpecialist(json, token!!)) {
                            MultipartUploadRequest(context, ItSpecialistsAdapter.BASE_URL+ "/upload_doc")
                                .addHeader("Authorization", "Bearer $token")
                                .setMethod("POST")
                                .addFileToUpload(filePath, "file")
                                .startUpload()
                            Log.d("NewCanLogs", "Archivo existe: $filePath")
                            showDialog = true
                        }
                    }else{
                        Log.d("NewCanLogs", "Archivo no existe: $filePath")
                    }

                }catch (e: Exception){
                    Log.e("NewCanLogs", "Error: ${e.message}")
                }
            }
        }
        valid.value = true
        clicked = false
    }

    Column (modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scrollState)
        .imePadding()
    ){
        Campo(nombre, context.getString(R.string.newCan_name), R.drawable.user, valid = valid, validators = arrayOf("Alphanumeric_es", "Required"))
        Campo(email, context.getString(R.string.email), R.drawable.mail, valid = valid, validators = arrayOf("Email", "Required"))
        SelectF(listOf(
            "Colombia",
            "Canada",
            "EE UU",
        ), nacionalidad, R.drawable.nacionalidad)
        Campo(profesion, context.getString(R.string.newCan_profesion), R.drawable.trabajo, valid = valid, validators = arrayOf("Alphanumeric_es", "Required"))
        SelectF(listOf(
            ".NET Junior Architect",
            ".NET Semi-Senior Architect",
            ".NET Senior Architect",
            ".NET Junior Developer",
            ".NET Semi-Senior Developer",
            ".NET Senior Developer",
            "Java Junior Architect",
            "Java Semi-Senior Architect",
            "Java Senior Architect",
            "Java Junior Developer",
            "Java Semi-Senior Developer",
            "Java Senior Developer",
        ), especialidad, R.drawable.trabajo)
        CampoMultilinea(resumen, context.getString(R.string.newCan_Resumen), valid = valid, validators = arrayOf("Alphanumeric_es", "Required"))
        Text(text = context.getString(R.string.newCan_docs), fontSize = 15.sp, modifier = Modifier.padding(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(color = MaterialTheme.colorScheme.surface)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(13.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = documentos.value,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 15.sp
                )
            }
            Button(
                onClick ={
                    pickFileResult.launch("application/pdf")
                }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Boton(
            context.getString(R.string.newCan_button),
            valid = valid,
            onClick = {
                clicked = true
            })

        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, RoundedCornerShape(13.dp))
                            .clip(RoundedCornerShape(13.dp))
                            .background(color = MaterialTheme.colorScheme.surface)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(13.dp)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.primary),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = context.getString(R.string.app_name),
                                fontSize = 15.sp,
                                modifier = Modifier.padding(10.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Text(
                            text = context.getString(R.string.newCan_exito),
                            modifier = Modifier.padding(10.dp),
                        )
                        if (docUri.value != null) {
                            Text(
                                text = context.getString(R.string.newCan_exito2),
                                modifier = Modifier.padding(10.dp),
                            )
                        }
                        Button(
                            modifier = Modifier.padding(10.dp),
                            onClick ={
                                showDialog = false
                                navController.navigate("home")
                        }) {
                            Text(context.getString(R.string.aceptar))
                        }

                    }
                }
            }
        }
    }
}

@SuppressLint("Range")
fun getPathFromUri(context: Context, uri: Uri, name:String): String {
    val inputStream = context.contentResolver.openInputStream(uri)
    val tempFile = File(context.cacheDir, name)
    inputStream?.use { input ->
        val outputStream = FileOutputStream(tempFile)
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
    return tempFile.absolutePath
}
@Preview(showBackground = true)
@Composable
fun NewCandidatePreview() {
    val context = LocalContext.current
    val title = remember { mutableStateOf(context.getString(R.string.layout_home)) }
    val img = remember { mutableIntStateOf(R.drawable.home) }
    NewCandidate(navController = NavController(context), title = title, img = img)
}