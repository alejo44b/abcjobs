package com.example.abcjobs.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class ItSpecialist(
    val userId: Int,
    val name: String,
    val email: String,
    val nationality: String,
    val profession: String,
    val speciality: String,
    val profile: String
):Parcelable

@Parcelize
data class ItSpecialistId(
    val id: Int,
    val userId: Int,
    val name: String,
    val email: String,
    val nationality: String,
    val profession: String,
    val speciality: String,
    val profile: String
):Parcelable
