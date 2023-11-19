package com.example.abcjobs.ui.candidate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.abcjobs.R
import com.example.abcjobs.ui.dashboard.Boton
import com.example.abcjobs.ui.dashboard.Campo
import com.example.abcjobs.ui.dashboard.CampoMultilinea
import com.example.abcjobs.ui.dashboard.Select
import com.google.accompanist.insets.imePadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun NewCandidate(title: MutableState<String>, img: MutableState<Int>) {
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

    val scrollState = rememberScrollState()
    val systemUiController = rememberSystemUiController()

    Column (modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scrollState)
        .imePadding()
    ){

        Campo(nombre, context.getString(R.string.newCan_name), R.drawable.user)
        Campo(email, context.getString(R.string.email), R.drawable.mail)
        Select(listOf(
            "Colombia",
            "Estados Unidos",
            "España",
            "Argentina"
        ), nacionalidad, R.drawable.nacionalidad)
        Campo(profesion, context.getString(R.string.newCan_profesion), R.drawable.trabajo)
        Select(listOf(
            "Ingeniería de sistemas",
            "Ingeniería de software",
            "Ingeniería de telecomunicaciones",
        ), especialidad, R.drawable.trabajo)
        CampoMultilinea(resumen, context.getString(R.string.newCan_Resumen))
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
                println("Siuuuu")
            })
    }
}

@Preview(showBackground = true)
@Composable
fun NewCandidatePreview() {
    val context = LocalContext.current
    val title = remember { mutableStateOf(context.getString(R.string.layout_home)) }
    val img = remember { mutableStateOf(R.drawable.home) }
    NewCandidate(title, img)
}