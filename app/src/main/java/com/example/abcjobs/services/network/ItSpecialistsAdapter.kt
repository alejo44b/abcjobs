package com.example.abcjobs.services.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlin.coroutines.suspendCoroutine

import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ItSpecialistsAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL = "http://10.0.2.2:3002"
        private var instance: ItSpecialistsAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ItSpecialistsAdapter(context).also {
                    instance = it
                }
            }
    }

    suspend fun pong(): Boolean = suspendCoroutine{ cont->
        requestQueue.add(getRequest("",
            {
                cont.resume(true)
            },{
                cont.resumeWithException(it)
            }))
    }
    suspend fun createItSpecialist(jsonItSpecialist:JSONObject, token: String):Boolean = suspendCoroutine{ cont->
        requestQueue.add(postRequest("/it_specialists",
            jsonItSpecialist,
            token,
            {
                Log.d("ItSpecialistsAdapter", it.toString())
                cont.resume(true)
            },{
                Log.e("ItSpecialistsAdapter", it.message.toString())
                cont.resume(false)
            }))
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL +path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject, token:String, responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener): JsonObjectRequest {
        return object : JsonObjectRequest(Request.Method.POST, BASE_URL + path, body, responseListener, errorListener) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL +path, body, responseListener, errorListener)
    }

}
