package com.example.pokemonfinder.features.pokemon_list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonfinder.R
import com.example.pokemonfinder.model.PokemonClickAction
import com.example.pokemonfinder.model.PokemonModel

class PokemonAdapter(
    private val context: Context,
    private var listPokemon: List<PokemonModel>,
    private val pokemonClickAction: PokemonClickAction
) : RecyclerView.Adapter<PokemonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        )

    override fun getItemCount(): Int = listPokemon.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemonAtual = listPokemon[position]

        Glide.with(context).load(pokemonAtual.sprites?.front_default).into(holder.imgPokemon)
        holder.nomePokemon.text = pokemonAtual.name?.capitalize()
        holder.idPokemon.text = pokemonAtual.formatPokemonID()

        holder.itemView.setOnClickListener {
            val animationClick = AnimationUtils.loadAnimation(context, R.anim.animation_click)
            animationClick.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    pokemonClickAction.clickPokemon(pokemonAtual.position)
                }

                override fun onAnimationStart(animation: Animation?) {}
            })

            holder.itemView.startAnimation(animationClick)
        }
    }

    fun filterList(filterList : List<PokemonModel>){
        this.listPokemon = filterList
        notifyDataSetChanged()
    }
}

class PokemonViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val imgPokemon = itemView.findViewById(R.id.imgPokemonItem) as ImageView
    val nomePokemon = itemView.findViewById(R.id.txtNomePokemonItem) as TextView
    val idPokemon = itemView.findViewById(R.id.txtIdPokemonItem) as TextView
}