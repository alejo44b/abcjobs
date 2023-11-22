package com.example.abcjobs.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Project(
    val city: String,
    val country: String,
    val department: String,
    val companyId: Double,
    val createdAt: String,
    val id: Double,
    val projectLeader: String,
    val projectLeaderPhone: Double,
    val projectName: String,
    val userId: Double
): Parcelable
