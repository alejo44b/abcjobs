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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.abcjobs.data.models.Select
import com.example.abcjobs.services.network.CompanyAdapter
import com.example.abcjobs.services.network.ItSpecialistsAdapter
import com.example.abcjobs.services.network.ProjectsAdapter
import com.example.abcjobs.services.network.SelectionAdapter
import com.example.abcjobs.ui.dashboard.Boton
import com.example.abcjobs.ui.dashboard.SelectId
import com.google.accompanist.insets.imePadding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDateTime

@SuppressLint("SuspiciousIndentation")
@Composable
fun Selection(navController: NavController, title: MutableState<String>, img: MutableState<Int>) {
    val context = LocalContext.current
    title.value = context.getString(R.string.selection_title)
    img.value = R.drawable.usuario_logo

    val project = remember { mutableStateOf(context.getString(R.string.selection_project)) }
    val team = remember { mutableStateOf(context.getString(R.string.selection_team)) }
    val candidate = remember { mutableStateOf(context.getString(R.string.selection_candidate)) }

    val projectId = remember { mutableIntStateOf(0) }
    val teamId = remember { mutableIntStateOf(0) }
    val candidateId = remember { mutableIntStateOf(0) }

    val company = remember { mutableStateOf(context.getString(R.string.selection_company)) }
    val projectList = remember { mutableStateOf(emptyArray<Select>()) }
    val teamList = remember { mutableStateOf(emptyArray<Select>()) }
    val candidateList = remember { mutableStateOf(emptyArray<Select>()) }

    var clicked by rememberSaveable { mutableStateOf(false) }
    val valid = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }

    val sharedPref = context.getSharedPreferences("auth", ComponentActivity.MODE_PRIVATE)
    val token = sharedPref.getString("token", null)
    val companyId = sharedPref.getInt("companyId", 0)

    LaunchedEffect(true){
        ProjectsAdapter.getInstance(context).getProjects(token?:"").forEach {
            val select = Select(it.id.toInt(), it.projectName)
            projectList.value += select
        }
    }

    LaunchedEffect(projectId.intValue){
        ProjectsAdapter.getInstance(context).getTeams(token?:"", projectId.intValue).forEach {
            val select = Select(it.id.toInt(), it.teamName)
            teamList.value += select
        }
    }

    LaunchedEffect(projectId.intValue){
        SelectionAdapter.getInstance(context).getItSpecialists(token?:"", projectId.intValue).forEach {
            val select = Select(it.id.toInt(), it.name)
            candidateList.value += select
        }
    }

    LaunchedEffect(true){
        company.value = CompanyAdapter.getInstance(context).getCompany(companyId, token?:"").name
    }

    LaunchedEffect(projectId.intValue, teamId.intValue, candidateId.intValue) {
        valid.value = !(projectId.intValue == 0 || teamId.intValue == 0 || candidateId.intValue == 0)
    }

    if (clicked) {
        val now = LocalDateTime.now().withSecond(0).withNano(0)
        val json = JSONObject()
            json.put("itSpecialistId", candidateId.intValue)
            json.put("companyId", companyId)
            json.put("projectId", projectId.intValue)
            json.put("teamId", teamId.intValue)
            json.put("selectionDate", now.toString())
        valid.value = false
        LaunchedEffect(Unit){
            withContext(Dispatchers.IO) {
                try {
                    if (SelectionAdapter.getInstance(context).createSelection(token?:"", json)) {
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
        Text(text = context.getString(R.string.selection_company), modifier = Modifier.padding(10.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(color = MaterialTheme.colorScheme.surfaceVariant),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Username Icon",
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 15.dp)
            )
            Text(text = company.value, modifier = Modifier.padding(10.dp))
        }
        SelectId(projectList.value.toList(), projectId, project, R.drawable.trabajo)
        SelectId(teamList.value.toList(), teamId, team, R.drawable.user)
        SelectId(candidateList.value.toList(), candidateId, candidate, R.drawable.user)
        Boton(
            context.getString(R.string.selection_button),
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
                            text = context.getString(R.string.selection_dialog),
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