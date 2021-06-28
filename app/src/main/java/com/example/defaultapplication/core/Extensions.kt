package com.example.defaultapplication.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.beust.klaxon.Klaxon
import com.example.defaultapplication.services.Event
import com.example.defaultapplication.services.ModelResponse
import com.example.defaultapplication.services.ServerResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type
import kotlin.reflect.KClass

inline fun <reified T> Type.parseTo(json: String): ModelResponse<T>  {

    return try {
        val myMosh = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val typeA = Types.newParameterizedType(ServerResponse::class.java, this::class.java)
        val jsonAdapter: JsonAdapter<ServerResponse<T>> = myMosh.adapter(typeA)
        val data = jsonAdapter.fromJson(json)!!

        return if (data.status) {
            ModelResponse.OnSuccess(data.data!!)
        } else {
            ModelResponse.OnError(data.error!!.toString())
        }

    } catch (e: Exception) {
        ModelResponse.OnError("HR001 Incompatible Data,${e.message}")
    }
}


 inline fun <reified T> String.parseJsonStringToClass(errorCode: String): ModelResponse<T> {

    return try {
        val myMosh = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val typeA = Types.newParameterizedType(ServerResponse::class.java, T::class.java)
        val jsonAdapter: JsonAdapter<ServerResponse<T>> = myMosh.adapter(typeA)
        val data = jsonAdapter.fromJson(this)!!

        return if (data.status) {
            ModelResponse.OnSuccess(data.data!!)
        } else {
            ModelResponse.OnError(data.error!!.toString())
        }

    } catch (e: Exception) {
        ModelResponse.OnError("$errorCode Incompatible Data,${e.message}")
    }
}

inline fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, crossinline onEventUnhandledContent: (T) -> Unit) {
    observe(owner, Observer { it?.getContentIfNotHandled()?.let(onEventUnhandledContent) })
}
