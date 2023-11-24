package com.example.abcjobs.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Performance(
    val itSpecialistId: Int,
    val teamId: Int,
    val evaluation: Int,
    val comments: String,
    val date: String
): Parcelable
