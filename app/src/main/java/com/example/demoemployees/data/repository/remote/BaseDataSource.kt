package com.example.demoemployees.data.repository.remote

import android.util.Log
import com.example.demoemployees.utils.Resource
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Resource.success(body)
                } else {
                    // podria no devolver datos...
                    return Resource.success()
                    // el 204 hay que tratarlo en algun lado. Dara success sin datos
                }
            }

            // TODO si el response.code es el 401 y no estamos en el login habria
            //  que redirigir al login en algun punto de la app. Sera que en un momento se ha logueado y ha caducado...
            //  otra opcion es hacer el login sin interaccion del usuario si tenemos guardado username y password...
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.e("PruebaDia1", message)
        return Resource.error("Network call has failed for a following reason: $message")
    }

}