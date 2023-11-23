package com.example.abcjobs.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Team(
    val createdAt: String,
    val id: Double,
    val projectId: Double,
    val teamLeader: String,
    val teamLeaderPhone: Double,
    val teamName: String
): Parcelable
