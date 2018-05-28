package com.lucasmoresco.marvel.api

import com.lucasmoresco.marvel.BuildConfig
import com.lucasmoresco.marvel.character.entities.Character
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object Factory {
        var retrofit: Retrofit? = null

        fun create(): ApiService {

            val builder = OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(BuildConfig.MARVEL_PUBLIC_KEY,
                            BuildConfig.MARVEL_PRIVATE_KEY,
                            TimeProvider()))

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)
            }

            val client = builder.build()

            val baseUrl = BuildConfig.API_URL
            retrofit = Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit!!.create(ApiService::class.java)
        }
    }

    @GET("characters")
    fun getCharacters(@Query("offset") offset: Int?): Call<Character>

}