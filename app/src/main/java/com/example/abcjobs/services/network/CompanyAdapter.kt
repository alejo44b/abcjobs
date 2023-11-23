package com.example.abcjobs.services.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.abcjobs.data.models.Company
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CompanyAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "http://10.0.2.2:3003"

        //const val BASE_URL = "http://lb-company-1765402036.us-east-1.elb.amazonaws.com"
        var instance: CompanyAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CompanyAdapter(context).also {
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
    suspend fun getCompany(id: Int, token: String): Company = suspendCoroutine { cont ->
        requestQueue.add(getRequestJson("/companies/$id",
            token,
            { response ->
                val company = Company(
                    address = response.getString("address"),
                    city = response.getString("city"),
                    companyId = response.getDouble("companyId"),
                    contact_name = response.getString("contact_name"),
                    contact_phone = response.getDouble("contact_phone"),
                    country = response.getString("country"),
                    dept = response.getString("dept"),
                    email = response.getString("email"),
                    name = response.getString("name"),
                    phone = response.getDouble("phone")
                )
                cont.resume(company)
            }, {
                cont.resume(
                    Company(
                    address = "",
                    city = "",
                    companyId = 0.0,
                    contact_name = "",
                    contact_phone = 0.0,
                    country = "",
                    dept = "",
                    email = "",
                    name = "",
                    phone = 0.0
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