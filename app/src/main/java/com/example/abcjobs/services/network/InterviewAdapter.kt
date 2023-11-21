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
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class InterviewAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "http://127.0.0.1:3001"
        //const val BASE_URL = "http://lb-interview-1765402036.us-east-1.elb.amazonaws.com"

        var instance: InterviewAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: InterviewAdapter(context).also {
                    instance = it
                }
            }
    }

    suspend fun pong(): Boolean = suspendCoroutine { cont ->
        requestQueue.add(getRequest("",
            {
                cont.resume(true)
            }, {
                cont.resume(false)
            }))
    }

    suspend fun getInterviews(token: String): JSONArray = suspendCoroutine { cont ->
        requestQueue.add(getRequestJsonArray("/interviewsList",
            token,
            { response ->
                cont.resume(response)
            }, {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getInterviewResult(id: Int, token: String): JSONObject = suspendCoroutine { cont ->
        requestQueue.add(getRequestJson("/interviews_results/$id",
            token,
            { response ->
                cont.resume(response)
            }, {
                cont.resumeWithException(it)
            }))
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, TechnicalTestAdapter.BASE_URL +path, responseListener,errorListener)
    }
    private fun getRequestJsonArray(path: String, token: String, responseListener: Response.Listener<JSONArray>, errorListener: Response.ErrorListener): JsonArrayRequest {
        return object : JsonArrayRequest(
            Request.Method.GET,
            TechnicalTestAdapter.BASE_URL + path,
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
            TechnicalTestAdapter.BASE_URL + path,
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
        return object : JsonObjectRequest(Request.Method.POST, TechnicalTestAdapter.BASE_URL + path, body, responseListener, errorListener) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
    }

}