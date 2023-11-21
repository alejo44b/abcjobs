package com.example.abcjobs.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InterviewResult(
    val comments: String,
    val date: String,
    val id: Int,
    val interviewId: Int,
    val result: Int
): Parcelable