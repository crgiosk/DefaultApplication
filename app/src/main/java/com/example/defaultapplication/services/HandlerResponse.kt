import com.example.defaultapplication.services.ServerResponse

import com.example.defaultapplication.services.ModelResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type

class HandlerResponse<T> {

    fun getClassFromJson(type: Class<T>,data: String = String(), typeResult: Type, result: (ModelResponse) -> Unit) {

        val myMosh = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        try {
            val typeA =
                Types.newParameterizedType(ServerResponse::class.java, typeResult::class.java)
            val jsonAdapter: JsonAdapter<ServerResponse<T>> = myMosh.adapter(typeA)
            val decoded = jsonAdapter.fromJson(data)!!

            if (decoded.status == "True") {
                result(ModelResponse.OnSuccess(decoded.data!!))
            } else {
                result(ModelResponse.OnError(decoded.error!!))
            }
        } catch (e: Exception) {
            result(ModelResponse.OnError("HR001 Incompatible Data,${e.message}"))
        }
    }
}


