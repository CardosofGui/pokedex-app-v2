package com.example.pokemonfinder.api

import com.example.pokemonfinder.model.PokemonModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {

    @GET("{dexNumOrName}")
    fun getPokemonByDexNumOrName(@Path("dexNumOrName") dexNumOrName: String?): Call<PokemonModel>
}