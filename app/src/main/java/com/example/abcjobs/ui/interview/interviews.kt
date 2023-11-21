package com.example.abcjobs.ui.interview

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.abcjobs.R
import com.example.abcjobs.data.models.Interview
import com.example.abcjobs.data.models.InterviewResult
import com.example.abcjobs.data.models.TechnicalTest
import com.example.abcjobs.data.models.TechnicalTestResult
import com.example.abcjobs.services.network.InterviewAdapter
import com.example.abcjobs.services.network.TechnicalTestAdapter

@Composable
fun Interviews(navController: NavController, title: MutableState<String>, img: MutableState<Int>) {
    val context = LocalContext.current

    val sharedPref = context.getSharedPreferences("auth", ComponentActivity.MODE_PRIVATE)
    val token = sharedPref.getString("token", null)

    val tests = remember { mutableStateOf(emptyArray<Interview>()) }

    LaunchedEffect(true){
        tests.value = InterviewAdapter.getInstance(context).getInterviews(token!!)
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(color = MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text = context.getString(R.string.tests_candidate),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .weight(1.5f)
                    .padding(10.dp),
                fontSize = 13.sp
            )
            Text(
                text = context.getString(R.string.tests_fecha),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .weight(2f)
                    .padding(10.dp),
                fontSize = 13.sp
            )
            Text(
                text = context.getString(R.string.tests_action),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                fontSize = 13.sp
            )
        }
        Divider(
            modifier = Modifier
                .padding(5.dp)
        )
        LazyColumn{
            items(tests.value) { item ->
                val result = remember { mutableStateOf(
                    InterviewResult(
                    id = 0,
                    interviewId = item.id,
                    result = 0,
                    date = "",
                    comments = ""
                )
                ) }
                LaunchedEffect(true){
                    result.value = InterviewAdapter.getInstance(context).getInterviewResult(item.id, token!!)
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .background(color = MaterialTheme.colorScheme.surfaceVariant),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(text = item.itSpecialistName, modifier = Modifier
                        .weight(1.5f)
                        .padding(10.dp),fontSize = 12.sp)
                    Text(text = item.date, modifier = Modifier
                        .weight(2f)
                        .padding(10.dp), fontSize = 12.sp)
                    if(result.value.id != 0) {
                        IconButton(
                            onClick = {
                                //navController.navigate("save_technical_test/${item.id}")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                Divider(
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun InterviewsPreview() {
    Interviews(navController = NavController(LocalContext.current), title = mutableStateOf(""), img = mutableIntStateOf(0))
}