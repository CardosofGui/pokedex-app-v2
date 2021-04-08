package com.example.pokemonfinder

import com.example.pokemonfinder.model.PokemonModel

object PokemonSingleton{
    var pokemonList = mutableListOf<PokemonModel>()
    var limitPokemon = 898
}