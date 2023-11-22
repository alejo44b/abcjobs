package com.example.abcjobs.ui.selection

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.abcjobs.R
import com.example.abcjobs.data.models.Interview
import com.example.abcjobs.data.models.Select
import com.example.abcjobs.services.network.ItSpecialistsAdapter
import com.example.abcjobs.services.network.ProjectsAdapter
import com.example.abcjobs.ui.dashboard.Boton
import com.example.abcjobs.ui.dashboard.Campo
import com.example.abcjobs.ui.dashboard.CampoMultilinea
import com.example.abcjobs.ui.dashboard.Select
import com.example.abcjobs.ui.dashboard.SelectId
import com.google.accompanist.insets.imePadding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

@Composable
fun Selection(navController: NavController, title: MutableState<String>, img: MutableState<Int>) {
    val context = LocalContext.current
    title.value = context.getString(R.string.menu_newCandidate)
    img.value = R.drawable.usuario_logo

    val company = remember { mutableStateOf(context.getString(R.string.selection_company)) }
    val project = remember { mutableStateOf(context.getString(R.string.selection_project)) }
    val team = remember { mutableStateOf(context.getString(R.string.selection_team)) }
    val candidate = remember { mutableStateOf(context.getString(R.string.selection_candidate)) }

    val companyId = remember { mutableIntStateOf(0) }
    val projectId = remember { mutableIntStateOf(0) }
    val teamId = remember { mutableIntStateOf(0) }
    val candidateId = remember { mutableIntStateOf(0) }

    val companyList = remember { mutableStateOf(context.getString(R.string.selection_company)) }
    val projectList = remember { mutableStateOf(emptyArray<Select>()) }
    val teamList = remember { mutableStateOf(emptyArray<Select>()) }
    val candidateList = remember { mutableStateOf(emptyArray<Select>()) }

    var clicked by rememberSaveable { mutableStateOf(false) }
    val valid = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }

    val sharedPref = context.getSharedPreferences("auth", ComponentActivity.MODE_PRIVATE)
    val token = sharedPref.getString("token", null)

    LaunchedEffect(true){
        ProjectsAdapter.getInstance(context).getProjects(token!!).forEach {
            val select = Select(it.id.toInt(), it.projectName)
            projectList.value += select
        }
    }

    LaunchedEffect(true){
        ProjectsAdapter.getInstance(context).getTeams(token!!).forEach {
            val select = Select(it.id.toInt(), it.teamName)
            teamList.value += select
        }
    }

    /*LaunchedEffect(nombre.value, email.value, nacionalidad.value, profesion.value, especialidad.value, resumen.value) {
        valid.value =
            if (nombre.value.isEmpty() || email.value.isEmpty()
                || nacionalidad.value == context.getString(R.string.newCan_Nacionalidad)
                || profesion.value.isEmpty() || especialidad.value == context.getString(R.string.newCan_Specialization)
                || resumen.value.isEmpty()) false else valid.value
    }*/

    /*if (clicked) {
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
    }*/

    Column (modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scrollState)
        .imePadding()
    ){
        Select(listOf(
            "Colombia",
            "Canada",
            "EE UU",
        ), company, R.drawable.nacionalidad)
        SelectId(projectList.value.toList(), projectId, project, R.drawable.trabajo)
        SelectId(teamList.value.toList(), teamId, team, R.drawable.trabajo)
        Select(listOf(
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
        ), candidate, R.drawable.trabajo)
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

@SuppressLint("UnrememberedMutableState")
@Preview (showBackground = true)
@Composable
fun PreviewSelection() {
    Selection(navController = NavController(LocalContext.current), title = mutableStateOf(""), img = mutableIntStateOf(0))
}