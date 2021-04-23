package com.example.pokemonfinder.framework.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitPokemon {

    private val retrofit : Retrofit

    fun pokemonAPI() : PokemonAPI{
        return retrofit.create(PokemonAPI::class.java)
    }

    companion object {
        private const val URL_API = "https://pokeapi.co/api/v2/"

        var retrofitPokemon : RetrofitPokemon? = null

        @get:Synchronized
        val instance : RetrofitPokemon?
            get(){
                if (retrofitPokemon == null){
                    retrofitPokemon = RetrofitPokemon()
                }
                return retrofitPokemon
            }
    }

    init {
        retrofit = Retrofit.Builder().baseUrl(URL_API)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}