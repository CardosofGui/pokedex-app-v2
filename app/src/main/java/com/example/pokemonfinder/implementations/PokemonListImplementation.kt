package com.example.pokemonfinder.implementations

import android.os.AsyncTask
import com.example.pokemonfinder.data.PokemonListData
import com.example.pokemonfinder.domain.PokemonModel
import com.example.pokemonfinder.domain.PokemonsApiResult
import com.example.pokemonfinder.framework.api.RetrofitPokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonListImplementation : PokemonListData {

    override fun getPokemon(nameOrNumber: String): PokemonModel {
        val response =
            RetrofitPokemon.instance?.pokemonAPI()?.getPokemonByDexNumOrName(nameOrNumber)?.execute()

        return if(response!!.isSuccessful){
            response.body()!!
        }else{
            PokemonModel(
                nameOrNumber.toInt(),
                "Erro",
                null,
                null,
                null,
                null,
                null
            )
        }
    }

    override fun getAllPokemonLimit(limit: Int): PokemonsApiResult {
        val response
                = RetrofitPokemon.instance?.pokemonAPI()?.getAllPokemonsLimit(limit)?.execute()

        return response?.body()!!
    }
}