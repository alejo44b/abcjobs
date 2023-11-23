package com.example.abcjobs.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    val address: String,
    val city: String,
    val companyId: Double,
    val contact_name: String,
    val contact_phone: Double,
    val country: String,
    val dept: String,
    val email: String,
    val name: String,
    val phone: Double
):Parcelable
