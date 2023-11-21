package com.example.abcjobs.services.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.abcjobs.data.models.Interview
import com.example.abcjobs.data.models.InterviewResult
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class InterviewAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "http://10.0.2.2:3001"
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
                Log.d("InterviewAdapter", "pong: $it")
                cont.resume(true)
            }, {
                Log.d("InterviewAdapter", "pong: $it")
                cont.resume(false)
            }))
    }
/*
    val companyId: Int,
    val companyName: String,
    val date: String,
    val id: Int,
    val itSpecialistId: Int,
    val itSpecialistName: String,
    val projectId: Int,
    val projectName: String,
    val result: Int
* */
    suspend fun getInterviews(token: String): Array<Interview> = suspendCoroutine { cont ->
        requestQueue.add(getRequestJsonArray("/interviewsList",
            token,
            { response ->
                val interviews = ArrayList<Interview>()
                for(i in 0 until response.length()){
                    val interview = response.getJSONObject(i)
                    interviews.add(Interview(
                        companyId = interview.getInt("companyId"),
                        companyName = interview.getString("companyName"),
                        date = interview.getString("date"),
                        id = interview.getInt("id"),
                        itSpecialistId = interview.getInt("itSpecialistId"),
                        itSpecialistName = interview.getString("itSpecialistName"),
                        projectId = interview.getInt("projectId"),
                        projectName = interview.getString("projectName"),
                        result = interview.getInt("result")
                    ))
                }

                Log.d("InterviewAdapter", "getInterviews: ${response.toString()}")
                cont.resume(interviews.toTypedArray())
            }, {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getInterview(id: Int, token: String): Interview = suspendCoroutine { cont ->
        requestQueue.add(getRequestJson("/interviews/$id",
            token,
            { response ->
                val interview = Interview(
                    companyId = response.getInt("companyId"),
                    companyName = response.getString("companyName"),
                    date = response.getString("date"),
                    id = response.getInt("id"),
                    itSpecialistId = response.getInt("itSpecialistId"),
                    itSpecialistName = response.getString("itSpecialistName"),
                    projectId = response.getInt("projectId"),
                    projectName = response.getString("projectName"),
                    result = response.getInt("result")
                )
                cont.resume(interview)
            }, {
                cont.resume(Interview(
                    companyId = 0,
                    companyName = "",
                    date = "",
                    id = 0,
                    itSpecialistId = 0,
                    itSpecialistName = "",
                    projectId = 0,
                    projectName = "",
                    result = 0
                ))
            }))
    }
    suspend fun getInterviewResult(id: Int, token: String): InterviewResult = suspendCoroutine { cont ->
        requestQueue.add(getRequestJson("/interviews_results/$id",
            token,
            { response ->
                val interviewResult = InterviewResult(
                    comments = response.getString("comments"),
                    date = response.getString("date"),
                    id = response.getInt("id"),
                    interviewId = response.getInt("interviewId"),
                    result = response.getInt("result")
                )
                cont.resume(interviewResult)
            }, {
                cont.resume(InterviewResult(
                    id = 0,
                    interviewId = 0,
                    result = 0,
                    date = "",
                    comments = ""
                ))
            }))
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
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