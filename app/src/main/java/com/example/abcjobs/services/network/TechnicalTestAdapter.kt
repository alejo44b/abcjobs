package com.example.abcjobs.services.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.abcjobs.data.models.TechnicalTest
import org.json.JSONArray
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TechnicalTestAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "http://10.0.2.2:3006"
        //const val BASE_URL = "http://lb-tectest-1541669127.us-east-1.elb.amazonaws.com"
        private var instance: TechnicalTestAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: TechnicalTestAdapter(context).also {
                    instance = it
                }
            }
    }

    suspend fun pong(): Boolean = suspendCoroutine { cont ->
        requestQueue.add(getRequest("",
            {
                cont.resume(true)
            }, {
                cont.resumeWithException(it)
            }))
    }
    suspend fun getTechnicalTests(token: String): Array<TechnicalTest> = suspendCoroutine { cont ->
        requestQueue.add(getRequestJsonArray("/technical_tests",
            token,
            { response ->
                val tests = ArrayList<TechnicalTest>()
                for(i in 0 until response.length()){
                    val test = response.getJSONObject(i)
                    tests.add(TechnicalTest(
                        companyId = test.getInt("companyId"),
                        companyName = test.getString("companyName"),
                        date = test.getString("date"),
                        id = test.getInt("id"),
                        itSpecialistId = test.getInt("itSpecialistId"),
                        itSpecialistName = test.getString("itSpecialistName"),
                        projectId = test.getInt("projectId"),
                        projectName = test.getString("projectName")
                    ))
                }

                cont.resume(tests.toTypedArray())
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
}