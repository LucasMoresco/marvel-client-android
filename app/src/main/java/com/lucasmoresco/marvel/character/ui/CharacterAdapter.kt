package com.lucasmoresco.marvel.character.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lucasmoresco.marvel.R
import com.lucasmoresco.marvel.character.entities.Result
import com.lucasmoresco.marvel.character.entities.Size
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_character.view.*


class CharacterAdapter(private var characters: MutableList<Result>?,
                       private val context: Context?, private val clickListener: (Result) -> Unit) : Adapter<CharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_character, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characters?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        characters?.let {
            val character = it[position]
            holder.bindView(character, clickListener)
        }
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        fun bindView(character: Result, clickListener: (Result) -> Unit) {

            itemView.setOnClickListener { clickListener(character) }

            val characterName = itemView.textCharacterName
            val characterDesc = itemView.textCharacterDescription
            val imageCharacter = itemView.imageCharacter

            characterName!!.text = character.name
            characterDesc!!.text = character.description
            Picasso.get().load(character.thumbnail.path + "/" + Size.PORTRAIT_MEDIUM.toString().toLowerCase() + ".jpg").centerCrop().fit().into(imageCharacter)

        }
    }

    fun setData(characters: List<Result>) {
        this.characters?.addAll(characters)
        notifyDataSetChanged()
    }

}