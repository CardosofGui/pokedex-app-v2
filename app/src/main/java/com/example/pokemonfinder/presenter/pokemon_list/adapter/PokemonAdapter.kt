package com.example.pokemonfinder.presenter.pokemon_list.adapter

import android.content.Context
import android.util.Log
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
import com.example.pokemonfinder.domain.click_interface.PokemonClickAction
import com.example.pokemonfinder.domain.PokemonResult

class PokemonAdapter(
    private val context: Context,
    private val pokemonClickAction: PokemonClickAction

) : RecyclerView.Adapter<PokemonViewHolder>() {

    private var listPokemon: List<PokemonResult> = arrayListOf()
    private var backupAllPokemons: List<PokemonResult> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        )

    override fun getItemCount(): Int = listPokemon.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemonAtual = listPokemon[position]

        Glide.with(context).load(pokemonAtual.getImage()).into(holder.imgPokemon)
        holder.nomePokemon.text = pokemonAtual.name?.capitalize()
        holder.idPokemon.text = pokemonAtual.formatPokemonID()

        holder.itemView.setOnClickListener {
            val animationClick = AnimationUtils.loadAnimation(context, R.anim.animation_click)
            animationClick.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    pokemonClickAction.clickPokemon(pokemonAtual.getNumberPokemon())
                }

                override fun onAnimationStart(animation: Animation?) {}
            })

            holder.itemView.startAnimation(animationClick)
        }
    }

    fun setPokemonList(listPokemon: List<PokemonResult>){
        this.listPokemon = listPokemon
        this.backupAllPokemons = listPokemon

        notifyDataSetChanged()
    }

    fun filterPokemons(searchPoke : String? = null,
                       firstFilterGen : Int? = null,
                       lastFilterGen : Int? = null){

        listPokemon = if(!searchPoke.isNullOrEmpty()){
            backupAllPokemons.filter {
                it.name.toLowerCase().contains(searchPoke.toLowerCase()) || it.getNumberPokemon().toString().contains(searchPoke)
            }
        }else{
            backupAllPokemons
        }

        if(firstFilterGen != null && lastFilterGen != null) {
            listPokemon = backupAllPokemons.filter {
                it.getNumberPokemon() >= firstFilterGen && it.getNumberPokemon() <= lastFilterGen
            }
        }

        notifyDataSetChanged()
    }
}

class PokemonViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val imgPokemon = itemView.findViewById(R.id.imgPokemonItem) as ImageView
    val nomePokemon = itemView.findViewById(R.id.txtNomePokemonItem) as TextView
    val idPokemon = itemView.findViewById(R.id.txtIdPokemonItem) as TextView
}