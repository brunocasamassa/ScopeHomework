package com.scope.application.utils

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/** this class uses reflection to embedded the request in a SafeResponse object,
 * easily to handle api errors with no crashes in the user's thread*/

sealed class SafeResponse<out T> {
    data class Success<out T>(val value: T) : SafeResponse<T>()
    data class GenericError(
        val code: Int? = null,
        val error: Response<*>? = null,
        val errorMessage: String
    ) : SafeResponse<Nothing>()

    data class NetworkError(val errorMessage: String) : SafeResponse<Nothing>()
}

/* basically a try catch intercepting the type of error*/
suspend fun <T> safeRequest(request: suspend () -> T): SafeResponse<T> {
    return try {
        SafeResponse.Success(request())
    } catch (throwable: Throwable) {
        Log.d(SafeResponse::class.java.toString(), throwable.message.toString())
        return when (throwable) {
            is IOException -> {
                SafeResponse.NetworkError(

                    throwable.localizedMessage ?: throwable.message.getOrSafe()

                )
            }
            is HttpException -> {
                SafeResponse.GenericError(
                    throwable.code(),
                    throwable.response(),
                    throwable.response()?.message().getOrSafe()
                )
            }
            else -> {
                SafeResponse.GenericError(
                    null,
                    null, throwable.localizedMessage ?: throwable.message.getOrSafe()
                )
            }
        }
    }
}


