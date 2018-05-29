package com.lucasmoresco.marvel.character

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.lucasmoresco.marvel.character.entities.Result

class CharacterViewModel : ViewModel() {

    private val characterRepository: CharacterRepository? = CharacterRepository()

    fun fetchCharacters(offset: Int?, query: String?): MutableLiveData<List<Result>>? {
        return characterRepository?.refreshCharacters(offset, query)
    }

}