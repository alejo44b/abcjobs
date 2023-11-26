package com.example.abcjobs.services.network

import VolleyMultipartRequest
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.abcjobs.data.models.ItSpecialistId
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import kotlin.coroutines.suspendCoroutine

import org.json.JSONObject
import java.io.File
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ItSpecialistsAdapter constructor(context: Context) {
    companion object{
        //const val BASE_URL = "http://10.0.2.2:3002"
        const val BASE_URL = "http://lb-it-specialist-1765799092.us-east-1.elb.amazonaws.com"
        private var instance: ItSpecialistsAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ItSpecialistsAdapter(context).also {
                    instance = it
                }
            }
    }

    private val client = OkHttpClient()

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
    suspend fun getItSpecialists(token: String):Array<ItSpecialistId> = suspendCoroutine {cont ->
        requestQueue.add(getRequestJsonArray("/it_specialists",
            token,
            {response ->
                val itSpecialists = ArrayList<ItSpecialistId>()
                for (i in 0 until response.length()){
                    val itSpecialist = response.getJSONObject(i)
                    itSpecialists.add(
                        ItSpecialistId(
                            id = itSpecialist.getInt("id"),
                            userId = itSpecialist.getInt("userId"),
                            name = itSpecialist.getString("name"),
                            email = itSpecialist.getString("email"),
                            nationality = itSpecialist.getString("nationality"),
                            profession = itSpecialist.getString("profession"),
                            speciality = itSpecialist.getString("speciality"),
                            profile = itSpecialist.getString("profile")
                        )
                    )
                }
                cont.resume(itSpecialists.toTypedArray())
            }, {
                cont.resumeWithException(it)
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

    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL +path, body, responseListener, errorListener)
    }

}
