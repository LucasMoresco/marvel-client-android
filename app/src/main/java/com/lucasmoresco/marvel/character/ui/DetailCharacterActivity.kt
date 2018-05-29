package com.lucasmoresco.marvel.character.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.lucasmoresco.marvel.R
import com.lucasmoresco.marvel.character.entities.Url
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_character.*
import java.util.*

class DetailCharacterActivity : AppCompatActivity() {

    private lateinit var characterUrl: String
    private lateinit var characterName: String
    private lateinit var characterDesc: String
    private lateinit var characterUrls: ArrayList<Url>
    private lateinit var detailUrl: Pair<String, String>
    private lateinit var detailWiki: Pair<String, String>
    private lateinit var detailComicLink: Pair<String, String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_character)

        intent.extras.let {
            characterUrl = it.getString("CHARACTER_IMAGE_URL")
            characterName = it.getString("CHARACTER_NAME")
            characterDesc = it.getString("CHARACTER_DESC")
            characterUrls = it.getParcelableArrayList<Url>("URLS")
        }

        setSupportActionBar(toolbarDetailCharacter as Toolbar?)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = characterName
        }

        setupView()
        showUrlButtons()
        handleButtonClick()

    }

    private fun handleButtonClick() {
        buttonActionDetail.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("TITLE", detailUrl.first)
            intent.putExtra("URL", detailUrl.second)
            startActivity(intent)
        }
        buttonActionWiki.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("TITLE", detailWiki.first)
            intent.putExtra("URL", detailWiki.second)
            startActivity(intent)
        }
        buttonActionComicLink.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("TITLE", detailComicLink.first)
            intent.putExtra("URL", detailComicLink.second)
            startActivity(intent)
        }
    }

    private fun showUrlButtons() {

        for (url in characterUrls) {
            when {
                url.type == "detail" -> {
                    buttonActionDetail.visibility = View.VISIBLE
                    detailUrl = Pair(url.type, url.url)
                }
                url.type == "wiki" -> {
                    buttonActionWiki.visibility = View.VISIBLE
                    detailWiki = Pair(url.type, url.url)
                }
                url.type == "comiclink" -> {
                    buttonActionComicLink.visibility = View.VISIBLE
                    detailComicLink = Pair(url.type, url.url)
                }
            }
        }
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