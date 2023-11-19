package com.example.abcjobs.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.example.abcjobs.R

@Composable
fun Home(title: MutableState<String>, img: MutableState<Int>){
    val context = LocalContext.current
    title.value = context.getString(R.string.layout_home)
    img.value = R.drawable.home
}