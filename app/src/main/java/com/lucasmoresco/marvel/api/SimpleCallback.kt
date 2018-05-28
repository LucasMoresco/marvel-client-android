package com.lucasmoresco.marvel.api

import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

abstract class SimpleCallback<T> : Callback<T> {

    var call: Call<T>? = null
        private set
    var response: Response<T>? = null
        private set
    var throwable: Throwable? = null
        private set

    override fun onResponse(call: Call<T>, response: Response<T>) {
        this.call = call
        this.response = response
        this.throwable = null
        if (response.isSuccessful) {
            success(response.body())
        } else {
            failure(RetrofitException.httpError(response.raw().request().url().toString(), response, ApiService.retrofit!!))
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        this.call = call
        this.response = null
        this.throwable = t
        val exception: RetrofitException = when (t) {
            is JSONException -> RetrofitException.unexpectedError(t)
            is IOException -> RetrofitException.networkError(t)
            else -> RetrofitException.unexpectedError(t)
        }
        failure(exception)
    }

    abstract fun success(t: T?)

    abstract fun failure(retrofitException: RetrofitException)

}