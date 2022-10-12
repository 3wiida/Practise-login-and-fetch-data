package com.example.testallthings.utils

import android.util.Log
import com.example.testallthings.network.model.errormodel.ErrorModel
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

sealed class ResultWrapper<out T> {
    object NetworkError : ResultWrapper<Nothing>()
    data class GenericError(val code: Int? = null, val message: String? = null) :
        ResultWrapper<Nothing>()

    data class Success<out T>(val result: T) : ResultWrapper<T>()
}

suspend fun <T> performSafeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> {
                Log.d("3wiida", "performSafeApiCall: ${throwable.message}")
                ResultWrapper.NetworkError

            }
            is HttpException -> {
                Log.d("3wiida", "performSafeApiCall: ${throwable.message}")
                var code = throwable.code()
                var message = convertErrorBody(throwable)
                ResultWrapper.GenericError(code, message)

            }
            else ->{
                Log.d("3wiida", "performSafeApiCall: ${throwable.message}")
                ResultWrapper.GenericError(code = null, message = throwable.message.toString())
            }
        }
    }
}

private fun convertErrorBody(e: HttpException): String {
    var error = Gson().fromJson(e.response()?.errorBody()?.string(), ErrorModel::class.java)
    return if (error.error == null) {
        error.message
    } else {
        var errorText = error.error!!.values.toList()[0].toString()
        errorText.slice(1 until errorText.length - 2)
    }
}

sealed class ApiState {
    object Empty : ApiState()
    object Loading : ApiState()
    data class Success<T>(val result: T?) : ApiState()
    object NetworkError : ApiState()
    data class GenericError(val code: Int? = null, val message: String? = null) : ApiState()
}