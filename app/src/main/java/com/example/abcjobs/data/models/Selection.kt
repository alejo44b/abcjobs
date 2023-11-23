package com.example.abcjobs.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Selection(
    val id: Int,
    val itSpecialistId: Int,
    val companyId: Int,
    val projectId: Int,
    val teamId: Int,
    val selectionDate: String
): Parcelable
