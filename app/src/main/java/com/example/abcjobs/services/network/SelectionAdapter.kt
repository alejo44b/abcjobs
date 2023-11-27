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
import com.example.abcjobs.data.models.ItSpecialistId
import com.example.abcjobs.data.models.Team
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SelectionAdapter constructor(context: Context) {
    companion object {
        //const val BASE_URL = "http://10.0.2.2:3007"
        const val BASE_URL = "http://lb-selections-416679298.us-east-1.elb.amazonaws.com"

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

    suspend fun createSelection(token: String, body: JSONObject): Boolean = suspendCoroutine { cont ->
        requestQueue.add(postRequest("/selections", body, token,
            {
                cont.resume(true)
            }, {
                cont.resume(false)
            }))
    }

    suspend fun getItSpecialists(token: String, id: Int):Array<ItSpecialistId> = suspendCoroutine { cont ->
        requestQueue.add(getRequestJsonArray("/selections/get_approved_it_specialists_by_project_id/$id",
            token,
            {response ->
                val itSpecialists = ArrayList<ItSpecialistId>()
                for (i in 0 until response.length()){
                    val itSpecialist = response.getJSONObject(i)
                    itSpecialists.add(
                        ItSpecialistId(
                            id = itSpecialist.getInt("itSpecialistId"),
                            userId = itSpecialist.getInt("companyId"),
                            name = itSpecialist.getString("itSpecialistName"),
                            email = itSpecialist.getString("itSpecialistName"),
                            nationality = itSpecialist.getString("itSpecialistName"),
                            profession = itSpecialist.getString("itSpecialistName"),
                            speciality = itSpecialist.getString("itSpecialistName"),
                            profile = itSpecialist.getString("itSpecialistName")
                        )
                    )
                }
                cont.resume(itSpecialists.toTypedArray())
            }, {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getTeams(token: String, id: Int): Array<Team> = suspendCoroutine { cont ->
        requestQueue.add(getRequestJsonArray("/teams_by_it_specialist_id/$id",
            token,
            { response ->
                val teams = ArrayList<Team>()
                for(i in 0 until response.length()){
                    val team = response.getJSONObject(i)
                    teams.add(
                        Team(
                        createdAt = team.getString("name"),
                        id = team.getDouble("id"),
                        projectId = team.getDouble("id"),
                        teamLeader = team.getString("name"),
                        teamLeaderPhone = team.getDouble("id"),
                        teamName = team.getString("name")
                    )
                    )
                }

                cont.resume(teams.toTypedArray())
            }, {
                cont.resumeWithException(it)
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
        Log.d("SelectionLogs", "postRequest: ${body.toString()}")

        return object : JsonObjectRequest(Request.Method.POST, BASE_URL + path, body, responseListener, errorListener) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

    }

}