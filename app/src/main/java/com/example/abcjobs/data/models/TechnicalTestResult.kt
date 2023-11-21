package com.example.abcjobs.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class TechnicalTestResult(
    val date: String,
    val id: Int,
    val result: Int,
    val technicalTestId: Int
): Parcelable
