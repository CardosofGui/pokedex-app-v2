package com.example.pokemonfinder.framework.api

import com.example.pokemonfinder.domain.PokemonModel
import com.example.pokemonfinder.domain.PokemonsApiResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonAPI {

    @GET("pokemon/{dexNumOrName}")
    fun getPokemonByDexNumOrName(@Path("dexNumOrName") dexNumOrName: String?): Call<PokemonModel>

    @GET("pokemon/")
    fun getAllPokemonsLimit(@Query("limit") limit : Int): Call<PokemonsApiResult>
}