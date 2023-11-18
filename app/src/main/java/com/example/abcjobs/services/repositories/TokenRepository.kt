package com.example.abcjobs.services.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.abcjobs.data.models.*
import com.example.abcjobs.services.network.Security
import org.json.JSONObject

class TokenRepository (private val application: Application) {
    suspend fun getToken( requestObject:JSONObject): Token {
        return Security.getInstance(application).auth(requestObject)
    }
}