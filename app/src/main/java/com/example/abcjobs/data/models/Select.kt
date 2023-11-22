package com.example.abcjobs.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Select(
    val id: Int,
    val name: String
):Parcelable
