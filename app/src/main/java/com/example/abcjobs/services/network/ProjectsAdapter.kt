package com.example.abcjobs.services.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.abcjobs.data.models.Project
import com.example.abcjobs.data.models.Team
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProjectsAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "http://10.0.2.2:3004"

        //const val BASE_URL = "http://lb-interview-1765402036.us-east-1.elb.amazonaws.com"
        var instance: ProjectsAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ProjectsAdapter(context).also {
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
    suspend fun getProjects(token: String): Array<Project> = suspendCoroutine { cont ->
        requestQueue.add(getRequestJsonArray("/projects",
            token,
            { response ->
                val projects = ArrayList<Project>()
                for(i in 0 until response.length()){
                    val project = response.getJSONObject(i)
                    projects.add(Project(
                        city = project.getString("city"),
                        country = project.getString("country"),
                        department = project.getString("department"),
                        companyId = project.getDouble("companyId"),
                        createdAt = project.getString("createdAt"),
                        id = project.getDouble("id"),
                        projectLeader = project.getString("projectLeader"),
                        projectLeaderPhone = project.getDouble("projectLeaderPhone"),
                        projectName = project.getString("projectName"),
                        userId = project.getDouble("userId")
                    ))
                }

                cont.resume(projects.toTypedArray())
            }, {
                cont.resumeWithException(it)
            }))
    }
    suspend fun getTeams(token: String): Array<Team> = suspendCoroutine { cont ->
        requestQueue.add(getRequestJsonArray("/teams",
            token,
            { response ->
                val teams = ArrayList<Team>()
                for(i in 0 until response.length()){
                    val team = response.getJSONObject(i)
                    teams.add(Team(
                        createdAt = team.getString("createdAt"),
                        id = team.getDouble("id"),
                        projectId = team.getDouble("projectId"),
                        teamLeader = team.getString("teamLeader"),
                        teamLeaderPhone = team.getDouble("teamLeaderPhone"),
                        teamName = team.getString("teamName")
                    ))
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
        return object : JsonObjectRequest(Request.Method.POST, BASE_URL + path, body, responseListener, errorListener) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
    }
}