package com.lucasmoresco.marvel

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lucasmoresco.marvel.character.CharacterViewModel
import com.lucasmoresco.marvel.character.entities.Result
import com.lucasmoresco.marvel.character.ui.CharacterAdapter
import com.lucasmoresco.marvel.character.ui.DetailCharacterActivity
import com.lucasmoresco.marvel.util.PaginationScrollListener
import kotlinx.android.synthetic.main.activity_main.*

const val ITEMS_PER_PAGE = 20

class MainActivity : AppCompatActivity() {

    private var characters: MutableList<Result>? = mutableListOf()
    private var characterViewModel: CharacterViewModel? = null
    private var characterAdapter: CharacterAdapter? = null
    private var currentPage = 0
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterViewModel = ViewModelProviders.of(this).get(CharacterViewModel::class.java)
        setupAdapter()
        getMarvelCharacters()

    }

    private fun setupAdapter() {
        characterAdapter = CharacterAdapter(characters, this, { character: Result -> characterItemClicked(character) })
        recyclerCharacter.adapter = characterAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerCharacter.layoutManager = linearLayoutManager

        recyclerCharacter.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {

            override fun getTotalPageCount(): Int {
                return ITEMS_PER_PAGE
            }

            override fun loadMoreItems() {
                isLoading = true
                currentPage += 20
                getMarvelCharacters()
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })

    }

    private fun characterItemClicked(character: Result) {
        val bundle = Bundle()
        bundle.putString("CHARACTER_IMAGE_URL", character.thumbnail.path)
        bundle.putString("CHARACTER_NAME", character.name)
        bundle.putString("CHARACTER_DESC", character.description)
        val intent = Intent(this, DetailCharacterActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }


    fun getMarvelCharacters() {

        progressLoadingContent.visibility = View.VISIBLE

        characterViewModel?.fetchCharacters(currentPage)?.observe(this, Observer<List<Result>> { characters ->

            characters?.let {

                progressLoadingContent.visibility = View.GONE
                isLoading = false

                characterAdapter?.setData(characters)

                if (characters.isEmpty()) {
                    textEmptyList.visibility = View.VISIBLE
                    recyclerCharacter.visibility = View.GONE
                } else {
                    textEmptyList.visibility = View.GONE
                    recyclerCharacter.visibility = View.VISIBLE
                }

            }

        })

    }
}