package com.example.pokemonfinder.data

class PokemonListDataRepository(private val pokemonListData: PokemonListData) {

    fun getPokemon(nameOrNumber : String) = pokemonListData.getPokemon(nameOrNumber)

    fun getAllPokemonLimit(limit : Int = 898) = pokemonListData.getAllPokemonLimit(limit)
}