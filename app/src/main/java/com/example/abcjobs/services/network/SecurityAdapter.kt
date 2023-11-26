package com.example.abcjobs.services.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

import com.example.abcjobs.data.models.*
import kotlin.coroutines.resumeWithException

class Security constructor(context: Context) {
    companion object{
        //const val BASE_URL = "http://10.0.2.2:3000"
        //const val BASE_URL = "http://192.168.0.9:3000"
        const val BASE_URL = "http://lb-security-689809691.us-east-1.elb.amazonaws.com"
        var instance: Security? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Security(context).also {
                    instance = it
                }
            }
    }

    suspend fun pong(): Boolean = suspendCoroutine{ cont->
        requestQueue.add(getRequest("",
            {
                cont.resume(true)
            },{
                cont.resume(false)
            }))
    }

    suspend fun auth(jsonUser:JSONObject, context: Context):Boolean = suspendCoroutine{ cont->
        requestQueue.add(postRequest("/users/auth",
            jsonUser,
            { response ->
                val token = Token(
                    companyId = response.optInt("companyId", 0),
                    createdAt = response.getString("createdAt"),
                    email = response.getString("email"),
                    expiredAt = response.getString("expireAt"),
                    id = response.getInt("id"),
                    role = response.getString("role"),
                    token = response.getString("token"),
                    username = response.getString("username")
                )
                val sharedPref = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putString("token", token.token)
                    putString("username", token.username)
                    putString("role", token.role)
                    putString("email", token.email)
                    putInt("companyId", token.companyId)
                    putString("id", token.id.toString())
                    putString("createdAt", token.createdAt)
                    putString("expiredAt", token.expiredAt)
                    apply()
                    cont.resume(true)
                }
            }, {
                cont.resumeWithException(it)
                Log.e("SecurityLogs", "Error: ${it.message}")
            }))
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }
}