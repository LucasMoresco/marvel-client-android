package com.lucasmoresco.marvel.api

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class RetrofitException internal constructor(message: String?,
                                             val url: String?,
                                             val response: Response<*>?,
                                             val kind: Kind, exception: Throwable?,
                                             val retrofit: Retrofit?) : RuntimeException(message, exception) {

    @Throws(IOException::class)
    fun <T> getErrorBodyAs(type: Class<T>): T? {
        if (response?.errorBody() == null) {
            return null
        }
        val converter = retrofit?.responseBodyConverter<T>(type, arrayOfNulls(0))
        return converter?.convert(response.errorBody())
    }

    companion object {

        fun httpError(url: String, response: Response<*>, retrofit: Retrofit): RetrofitException {
            val message = response.code().toString() + " " + response.message()
            return RetrofitException(message, url, response, Kind.HTTP, null, retrofit)
        }

        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(exception.message!!, null, null, Kind.NETWORK, exception, null)
        }

        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(exception.message!!, null, null, Kind.UNEXPECTED, exception, null)
        }
    }

    enum class Kind {
        NETWORK,
        HTTP,
        UNEXPECTED
    }
}