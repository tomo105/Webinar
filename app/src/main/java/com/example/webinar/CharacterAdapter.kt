package com.example.webinar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    private val characters : MutableList<Character> by lazy { mutableListOf<Character>() }

    fun setCharacters(characters: List<Character>){
        if(this.characters.isNotEmpty()){
            this.characters.clear()
        }


        this.characters.addAll(characters)
        notifyDataSetChanged()   //przebudowc zeby pokazac dane po zmianie
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_character, parent, false)

        return CharacterViewHolder(itemView)  //
       }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
      }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(character: Character) {
            itemView.textView.text = character.name

            Glide.with(itemView)  //odczytac obrazek
                .load(character.image)
                .into(itemView.imageView)
        }
    }
}