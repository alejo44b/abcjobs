package com.example.abcjobs.services.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SelectionAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "http://10.0.2.2:3007"
        //const val BASE_URL = "http://lb-selection-1765402036.us-east-1.elb.amazonaws.com"

        var instance: SelectionAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: SelectionAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun pong(): Boolean = suspendCoroutine { cont ->
        requestQueue.add(getRequest("",
            {
                cont.resume(true)
            }, {
                cont.resume(false)
            }))
    }



    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL +path, responseListener,errorListener)
    }
    private fun getRequestJsonArray(path: String, token: String, responseListener: Response.Listener<JSONArray>, errorListener: Response.ErrorListener): JsonArrayRequest {
        return object : JsonArrayRequest(
            Request.Method.GET,
            BASE_URL + path,
            null,
            responseListener,
            errorListener
        ) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
    }
    private fun getRequestJson(path: String, token: String, responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener): JsonObjectRequest {
        return object : JsonObjectRequest(
            Request.Method.GET,
            BASE_URL + path,
            null,
            responseListener,
            errorListener
        ) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
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

}