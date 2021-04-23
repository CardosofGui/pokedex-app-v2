package com.example.pokemonfinder.data

import com.example.pokemonfinder.domain.PokemonModel

object PokemonSingleton{
    var pokemonList = mutableListOf<PokemonModel>()
    var limitPokemon = 898
}