package com.example.abcjobs.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Interview(
    val companyId: Int,
    val companyName: String,
    val date: String,
    val id: Int,
    val itSpecialistId: Int,
    val itSpecialistName: String,
    val projectId: Int,
    val projectName: String,
    val result: Int
): Parcelable
