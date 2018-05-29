package com.lucasmoresco.marvel

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lucasmoresco.marvel.character.ui.CharactersActivity
import java.util.*

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@LaunchActivity, CharactersActivity::class.java))
                finish()
            }
        }, 3000)
    }
}