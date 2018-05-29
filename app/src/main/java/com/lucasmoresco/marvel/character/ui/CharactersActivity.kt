package com.lucasmoresco.marvel.character.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.lucasmoresco.marvel.R
import com.lucasmoresco.marvel.character.CharacterViewModel
import com.lucasmoresco.marvel.character.entities.Result
import com.lucasmoresco.marvel.util.PaginationScrollListener
import kotlinx.android.synthetic.main.activity_characters.*

const val ITEMS_PER_PAGE = 20

class CharactersActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var characters: MutableList<Result>? = mutableListOf()
    private var characterViewModel: CharacterViewModel? = null
    private var characterAdapter: CharacterAdapter? = null
    private var currentPage = 0
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        characterViewModel = ViewModelProviders.of(this).get(CharacterViewModel::class.java)
        setupAdapter()
        setupSearchView()
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

    private fun setupSearchView() {
        val searchClose = searchView.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_close_btn)
        searchClose?.setColorFilter(R.color.black)
        val searchEditText = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
        searchEditText?.setTextColor(ContextCompat.getColor(this, R.color.black))
        searchEditText?.setTextColor(ContextCompat.getColor(this, R.color.black))
        searchEditText?.setHintTextColor(ContextCompat.getColor(this, R.color.gray_light4))

        searchView.isActivated = true
        searchView.queryHint = getString(R.string.search_title)
        searchView.onActionViewExpanded()
        searchView.isIconified = false
        searchView.clearFocus()
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        currentPage = 0
        characters?.clear()
        getMarvelCharacters(query)
        return true
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        when {
            newText!!.isBlank() -> {
                characters?.clear()
                getMarvelCharacters()
            }
        }
        return true
    }

    fun getMarvelCharacters(query: String? = null) {

        progressLoadingContent.visibility = View.VISIBLE
        textEmptyList.visibility = View.GONE

        characterViewModel?.fetchCharacters(currentPage, query)?.observe(this, Observer<List<Result>> { characters ->

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

    private fun characterItemClicked(character: Result) {
        val bundle = Bundle()
        bundle.putString("CHARACTER_IMAGE_URL", character.thumbnail.path)
        bundle.putString("CHARACTER_NAME", character.name)
        bundle.putString("CHARACTER_DESC", character.description)
        bundle.putParcelableArrayList("URLS", character.urls)
        val intent = Intent(this, DetailCharacterActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}