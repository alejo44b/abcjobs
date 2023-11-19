package com.example.abcjobs.ui.technical_tests

import android.util.Log
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
import com.example.abcjobs.R
import com.example.abcjobs.data.models.TechnicalTest
import com.example.abcjobs.services.network.TechnicalTestAdapter

@Composable
fun TechnicalTests(title: MutableState<String>, img: MutableState<Int>) {
    val context = LocalContext.current
    title.value = context.getString(R.string.tecnical_tests)
    img.value = R.drawable.test
    val sharedPref = context.getSharedPreferences("auth", ComponentActivity.MODE_PRIVATE)
    val token = sharedPref.getString("token", null)

    val tests = remember { mutableStateOf(emptyArray<TechnicalTest>()) }

    LaunchedEffect(true){
        tests.value = TechnicalTestAdapter.getInstance(context).getTechnicalTests(token!!)
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
                    IconButton(
                        onClick = {}
                    ){
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
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

@Preview (showBackground = true)
@Composable
fun TechnicalTestsPreview() {
    val context = LocalContext.current
    val title = remember { mutableStateOf(context.getString(R.string.layout_home)) }
    val img = remember { mutableIntStateOf(R.drawable.home) }
    TechnicalTests(title = title, img = img)
}