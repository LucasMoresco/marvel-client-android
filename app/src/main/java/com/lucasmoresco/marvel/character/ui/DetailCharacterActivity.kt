package com.lucasmoresco.marvel.character.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.lucasmoresco.marvel.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_character.*

class DetailCharacterActivity : AppCompatActivity() {


    private lateinit var characterUrl: String
    private lateinit var characterName: String
    private lateinit var characterDesc: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_character)

        intent.extras.let {
            characterUrl = it.getString("CHARACTER_IMAGE_URL")
            characterName = it.getString("CHARACTER_NAME")
            characterDesc = it.getString("CHARACTER_DESC")
        }

        setSupportActionBar(toolbarDetailCharacter as Toolbar?)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = characterName
        }

        setupView()

    }

    private fun setupView() {
        Picasso.get().load("$characterUrl.jpg").centerCrop().fit().into(imageCharacter)
        textCharacterName.text = characterName
        textCharacterDescription.text = characterDesc
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}