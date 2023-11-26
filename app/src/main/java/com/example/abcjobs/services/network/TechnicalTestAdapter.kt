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
import com.example.abcjobs.data.models.TechnicalTest
import com.example.abcjobs.data.models.TechnicalTestResult
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TechnicalTestAdapter constructor(context: Context) {
    companion object {
        //const val BASE_URL = "http://10.0.2.2:3006"
        const val BASE_URL = "http://lb-techtest-617673962.us-east-1.elb.amazonaws.com"
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

    suspend fun getResult(id: Int, token: String): TechnicalTestResult = suspendCoroutine { cont ->
        requestQueue.add(getRequestJson("/technical_tests_results_by_technical_test_id/$id",
            token,
            { testResult ->
                val testResult = TechnicalTestResult(
                    date = testResult.getString("date"),
                    id = testResult.getInt("id"),
                    result = testResult.getInt("result"),
                    technicalTestId = testResult.getInt("technicalTestId")
                )
                cont.resume(testResult)
            }, {
                cont.resume(TechnicalTestResult(
                    id = 0,
                    technicalTestId = 0,
                    result = 0,
                    date = ""
                ))
            }))
    }
    suspend fun getTechnicalTest(id: Int, token: String): TechnicalTest = suspendCoroutine { cont ->
        requestQueue.add(getRequestJson("/technical_tests/$id",
            token,
            { test ->
                    val testTech =TechnicalTest(
                        companyId = test.getInt("companyId"),
                        companyName = test.getString("companyName"),
                        date = test.getString("date"),
                        id = test.getInt("id"),
                        itSpecialistId = test.getInt("itSpecialistId"),
                        itSpecialistName = test.getString("itSpecialistName"),
                        projectId = test.getInt("projectId"),
                        projectName = test.getString("projectName")
                    )
                cont.resume(testTech)
            }, {
                cont.resumeWithException(it)
            }))
    }

    suspend fun addResult(jsonItSpecialist:JSONObject, token: String):Boolean = suspendCoroutine{ cont->
        requestQueue.add(postRequest("/technical_tests_results",
            jsonItSpecialist,
            token,
            {
                Log.d("TechnicalTestLogs", it.toString())
                cont.resume(true)
            },{
                Log.e("TechnicalTestLogs", it.message.toString())
                cont.resume(false)
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