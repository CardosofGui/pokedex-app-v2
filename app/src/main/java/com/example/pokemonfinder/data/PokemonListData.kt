package com.example.pokemonfinder.data

import com.example.pokemonfinder.domain.PokemonModel
import com.example.pokemonfinder.domain.PokemonsApiResult

interface PokemonListData {
    fun getPokemon(nameOrNumber: String): PokemonModel

    fun getAllPokemonLimit(limit : Int = 898): PokemonsApiResult
}