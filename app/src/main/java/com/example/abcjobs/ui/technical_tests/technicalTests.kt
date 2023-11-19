package com.example.abcjobs.ui.technical_tests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abcjobs.R
import com.example.abcjobs.data.models.TechnicalTest

@Composable
fun TechnicalTests(title: MutableState<String>, img: MutableState<Int>) {
    val context = LocalContext.current
    title.value = context.getString(R.string.tecnical_tests)
    img.value = R.drawable.test
    val techTests = listOf(
        TechnicalTest(
            companyId = 1,
            companyName = "ABC",
            date = "2023-11-09T14:17:00",
            id = 1,
            itSpecialistId = 1,
            itSpecialistName = "Daniel",
            projectId = 1,
            projectName = "Desarrollo Back"
        )
    )

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
            items(techTests) { item ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .background(color = MaterialTheme.colorScheme.surfaceVariant),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(text = item.itSpecialistName, modifier = Modifier.weight(1.5f).padding(10.dp),fontSize = 12.sp)
                    Text(text = item.date, modifier = Modifier.weight(2f).padding(10.dp), fontSize = 12.sp)
                    Text(text = "", modifier = Modifier.weight(1f).padding(10.dp), fontSize = 12.sp)
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
fun TecnhicalTestsPreview() {
    val context = LocalContext.current
    val title = remember { mutableStateOf(context.getString(R.string.layout_home)) }
    val img = remember { mutableIntStateOf(R.drawable.home) }
    TechnicalTests(title = title, img = img)
}