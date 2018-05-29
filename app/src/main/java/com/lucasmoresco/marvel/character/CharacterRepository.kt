package com.lucasmoresco.marvel.character

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.lucasmoresco.marvel.api.ApiService
import com.lucasmoresco.marvel.api.RetrofitException
import com.lucasmoresco.marvel.api.SimpleCallback
import com.lucasmoresco.marvel.character.entities.Character
import com.lucasmoresco.marvel.character.entities.Result

class CharacterRepository {

    fun refreshCharacters(offset: Int?, query: String?): MutableLiveData<List<Result>> {

        val data = MutableLiveData<List<Result>>()
        val apiService = ApiService.create()

        apiService.getCharacters(offset, query).enqueue(object : SimpleCallback<Character>() {

            override fun success(t: Character?) {

                response?.body()?.let {
                    data.value = it.data.results
                }
            }

            override fun failure(retrofitException: RetrofitException) {
                Log.d("MARVEL_CLIENT_ERROR_API", retrofitException.message)
            }

        })

        return data
    }

}