package com.example.abcjobs.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Token(
    val companyId: Int,
    val createdAt: String,
    val email: String,
    val expiredAt: String,
    val id: Int,
    val role: String,
    val token: String,
    val username: String
): Parcelable