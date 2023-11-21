package com.example.abcjobs.ui.interview

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.abcjobs.R
import com.example.abcjobs.data.models.Interview
import com.example.abcjobs.data.models.InterviewResult
import com.example.abcjobs.data.models.TechnicalTest
import com.example.abcjobs.services.network.InterviewAdapter
import com.example.abcjobs.services.network.TechnicalTestAdapter
import com.example.abcjobs.ui.dashboard.Boton
import com.example.abcjobs.ui.dashboard.Campo
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
fun InterviewResult(navController: NavController, title: MutableState<String>, img: MutableState<Int>, id: Int, candidate: String) {
    val context = LocalContext.current
    title.value = context.getString(R.string.layout_interview)
    img.value = R.drawable.test

    val date = remember { mutableStateOf(LocalDate.now()) }
    val time = remember { mutableStateOf(LocalTime.now().withSecond(0)) }
    val resultado = remember { mutableStateOf("") }
    val datetime = remember { mutableStateOf("${date.value}T${time.value.format(DateTimeFormatter.ofPattern("HH:mm:ss"))}") }

    var clicked by rememberSaveable { mutableStateOf(false) }
    val valid = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }

    val dialogState = rememberMaterialDialogState()
    val timeState = rememberMaterialDialogState()

    val interviewResult = remember{ mutableStateOf(
        InterviewResult(
            comments = "",
            date = "",
            id = 0,
            interviewId = 0,
            result = 0
        )
    )}

    val sharedPref = context.getSharedPreferences("auth", ComponentActivity.MODE_PRIVATE)
    val token = sharedPref.getString("token", null)

    LaunchedEffect(resultado.value) {
        valid.value = if (resultado.value.isEmpty()) false else valid.value
    }

    LaunchedEffect(date.value, time.value) {
        datetime.value = "${date.value}T${time.value.format(DateTimeFormatter.ofPattern("HH:mm:ss"))}"
        valid.value = resultado.value.isNotEmpty()
    }

    LaunchedEffect(true){
        interviewResult.value = InterviewAdapter.getInstance(context).getInterviewResult(id, token!!)
    }

    Column (modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scrollState)
        .imePadding()
    ){

        Text(text = context.getString(R.string.newCan_name), modifier = Modifier.padding(10.dp))
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
            Text(text = candidate, modifier = Modifier.padding(10.dp))
        }

        Text(text = context.getString(R.string.interview_resultado), modifier = Modifier.padding(10.dp))
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
            Text(text = interviewResult.value.result.toString(), modifier = Modifier.padding(10.dp))
        }
        Text(text = context.getString(R.string.interview_fecha), modifier = Modifier.padding(10.dp))
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
            Text(text = interviewResult.value.date, modifier = Modifier.padding(10.dp))
        }
        Text(text = context.getString(R.string.interview_comment), modifier = Modifier.padding(10.dp))
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
            Text(text = interviewResult.value.comments, modifier = Modifier.padding(10.dp))
        }

        Divider(modifier = Modifier.padding(10.dp))

        Boton(
            label = context.getString(R.string.interview_atras),
            onClick = {
                navController.popBackStack()
            })
    }
}