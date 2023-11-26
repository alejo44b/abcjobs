package com.example.abcjobs.ui.performance

import android.util.Log
import androidx.activity.ComponentActivity
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.abcjobs.services.network.PerformanceAdapter
import com.example.abcjobs.services.network.ProjectsAdapter
import com.example.abcjobs.ui.dashboard.Boton
import com.example.abcjobs.ui.dashboard.Campo
import com.example.abcjobs.ui.dashboard.SelectId
import com.google.accompanist.insets.imePadding
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun Performance(navController: NavController, title: MutableState<String>, img: MutableState<Int>) {
    val context = LocalContext.current
    title.value = context.getString(R.string.layout_performance)
    img.value = R.drawable.usuario_logo

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

    val dialogState = rememberMaterialDialogState()
    val timeState = rememberMaterialDialogState()

    val date = remember { mutableStateOf(LocalDate.now()) }
    val time = remember { mutableStateOf(LocalTime.now().withSecond(0)) }
    val result = remember { mutableStateOf("") }
    val comment = remember { mutableStateOf("") }

    val datetime = remember { mutableStateOf("${date.value}T${time.value.format(DateTimeFormatter.ofPattern("HH:mm:ss"))}") }


    LaunchedEffect(true){
        ProjectsAdapter.getInstance(context).getTeams(token?:"").forEach {
            val select = Select(it.id.toInt(), it.teamName)
            teamList.value += select
        }
    }

    LaunchedEffect(true){
        ItSpecialistsAdapter.getInstance(context).getItSpecialists(token?:"").forEach {
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
        val json = JSONObject()
        json.put("itSpecialistId", candidateId.intValue)
        json.put("teamId", teamId.intValue)
        json.put("evaluation", result.value.toInt())
        json.put("comments", comment.value)
        json.put("date", datetime.value)

        valid.value = false
        LaunchedEffect(Unit){
            withContext(Dispatchers.IO) {
                try {
                    if (PerformanceAdapter.getInstance(context).addPerformance(json, token!!)) {
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
        Text(text = context.getString(R.string.selection_candidate), modifier = Modifier.padding(10.dp))
        SelectId(candidateList.value.toList(), candidateId, candidate, R.drawable.user)
        Text(text = context.getString(R.string.selection_team), modifier = Modifier.padding(10.dp))
        SelectId(teamList.value.toList(), teamId, team, R.drawable.user)
        Text(text = context.getString(R.string.save_test_resultado), modifier = Modifier.padding(10.dp))
        Campo(result, context.getString(R.string.save_test_resultado), R.drawable.docs, valid = valid, validators = arrayOf("Numeric", "Required"))
        Text(text = context.getString(R.string.save_test_comment), modifier = Modifier.padding(10.dp))
        Campo(comment, context.getString(R.string.save_test_comment), R.drawable.docs, valid = valid, validators = arrayOf("Numeric", "Required"))
        Text(text = context.getString(R.string.save_test_date), modifier = Modifier.padding(10.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(color = MaterialTheme.colorScheme.surfaceVariant),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = datetime.value, modifier = Modifier.padding(10.dp))
            Row {
                IconButton(
                    onClick = {
                        dialogState.show()
                    }
                ){
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }

                IconButton(
                    onClick = {
                        timeState.show()
                    }
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.time),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }
        }


        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            },
        ) {
            datepicker(initialDate = date.value, onDateChange = { date.value = it })
        }

        MaterialDialog(
            dialogState = timeState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            }
        ) {
            timepicker(initialTime = time.value, onTimeChange = { time.value = it })
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

@Preview (showBackground = true)
@Composable
fun PerformancePreview() {
    val title = remember { mutableStateOf("Title") }
    val img = remember { mutableIntStateOf(R.drawable.usuario_logo) }
    Performance(navController = NavController(LocalContext.current), title = title, img = img)
}