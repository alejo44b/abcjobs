package com.example.abcjobs.ui.candidate

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.abcjobs.Dashboard
import com.example.abcjobs.R
import com.example.abcjobs.services.network.ItSpecialistsAdapter
import com.example.abcjobs.services.network.Security
import com.example.abcjobs.ui.dashboard.Boton
import com.example.abcjobs.ui.dashboard.Campo
import com.example.abcjobs.ui.dashboard.CampoMultilinea
import com.example.abcjobs.ui.dashboard.Select
import com.google.accompanist.insets.imePadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.Locale

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

    var clicked by rememberSaveable { mutableStateOf(false) }
    val valid = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }

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
                    if (ItSpecialistsAdapter.getInstance(context).createItSpecialist(json, token!!)) {
                        showDialog = true
                    }
                }catch (e: Exception){
                    Log.e("LoginLogs", "Error: ${e.message}")
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

        Campo(nombre, context.getString(R.string.newCan_name), R.drawable.user, valid = valid)
        Campo(email, context.getString(R.string.email), R.drawable.mail, valid = valid)
        Select(listOf(
            "Colombia",
            "Estados Unidos",
            "España",
            "Argentina"
        ), nacionalidad, R.drawable.nacionalidad)
        Campo(profesion, context.getString(R.string.newCan_profesion), R.drawable.trabajo, valid = valid)
        Select(listOf(
            "Ingeniería de sistemas",
            "Ingeniería de software",
            "Ingeniería de telecomunicaciones",
        ), especialidad, R.drawable.trabajo)
        CampoMultilinea(resumen, context.getString(R.string.newCan_Resumen), valid = valid)
        /*Row(){
            Campo(documentos, context.getString(R.string.newCan_docs), R.drawable.docs)
            Boton(
                context.getString(R.string.newCan_button),
                onClick = {
                    println("Siuuuu")
                }
            )
        }*/
        Boton(
            context.getString(R.string.newCan_button),
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
                            text ="¡Candidato registrado con exito!",
                            modifier = Modifier.padding(10.dp),
                        )
                        Button(
                            modifier = Modifier.padding(10.dp),
                            onClick ={
                                showDialog = false
                                navController.navigate("home")
                        }) {
                            Text("Aceptar")
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewCandidatePreview() {
    val context = LocalContext.current
    val title = remember { mutableStateOf(context.getString(R.string.layout_home)) }
    val img = remember { mutableStateOf(R.drawable.home) }
    NewCandidate(navController = NavController(context), title = title, img = img)
}