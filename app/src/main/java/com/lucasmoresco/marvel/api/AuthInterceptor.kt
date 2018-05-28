package com.lucasmoresco.marvel.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


internal class AuthInterceptor(private val publicKey: String, private val privateKey: String, private val timeProvider: TimeProvider) : Interceptor {
    private val authHashGenerator = AuthHashGenerator()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val timestamp = timeProvider.currentTimeMillis()
        var hash: String? = null
        try {
            hash = authHashGenerator.generateHash(timestamp.toString(), publicKey, privateKey)
        } catch (e: MarvelApiException) {
            e.printStackTrace()
        }

        var request = chain.request()
        val url = request.url()
                .newBuilder()
                .addQueryParameter(TIMESTAMP_KEY, timestamp.toString())
                .addQueryParameter(APIKEY_KEY, publicKey)
                .addQueryParameter(HASH_KEY, hash)
                .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    companion object {
        private const val TIMESTAMP_KEY = "ts"
        private const val HASH_KEY = "hash"
        private const val APIKEY_KEY = "apikey"
    }




}