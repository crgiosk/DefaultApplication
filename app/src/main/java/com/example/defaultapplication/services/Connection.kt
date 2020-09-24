package com.example.defaultapplication.services

import android.util.Log
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

internal object Connection {

    const val ERROR_CONNECTION = "I am NOT detecting data connection, please check your network."
    const val ERROR_SERVER = "Oops! Something went wrong.\n Feel free to reach us at support@mdcloudps.com, if the issue persists."
    const val ERROR_SECURITY = "Oops! An error has occurred.\n You will need to restart the App and log back in. We apologize for the inconvenience."

    internal fun send(endpoint: Endpoint, values: MutableMap<String, Any>, completion: (ConnectionResponse) -> Unit) {
        val  client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        //val url = "https://omdbapi.com/?s=Cars&apikey=5d690030"
        val url = "https://${endpoint.base}/${endpoint.path}"
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.i(Connection::class.java.name, "$url OnFailure ${e?.printStackTrace().toString()}")
                completion(ConnectionResponse.OnFailure)
            }

            override fun onResponse(call: Call?, response: Response?) {
                if (response!!.isSuccessful) {
                    if (response.body() != null) {
                        Log.i(Connection::class.java.name, "$url OnSuccess")
                        completion(ConnectionResponse.OnSuccess(response.body()!!.string()))
                    } else {
                        Log.i(Connection::class.java.name, "$url OnInvalidData")
                        completion(ConnectionResponse.OnInvalidData)
                    }
                } else {
                    Log.i(Connection::class.java.name, "$url OnResponseUnsuccessful")
                    completion(ConnectionResponse.OnResponseUnsuccessful(response.code()))
                }
            }
        })
    }

}