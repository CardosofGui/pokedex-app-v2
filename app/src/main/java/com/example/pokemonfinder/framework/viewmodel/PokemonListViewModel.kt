package com.example.pokemonfinder.framework.viewmodel

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonfinder.data.PokemonListDataRepository
import com.example.pokemonfinder.domain.PokemonModel
import com.example.pokemonfinder.domain.PokemonResult
import com.example.pokemonfinder.domain.PokemonsApiResult
import com.example.pokemonfinder.implementations.PokemonListImplementation

class PokemonListViewModel : ViewModel() {
    private val pokemonListImplementation = PokemonListImplementation()
    private val pokemonListDataRepository = PokemonListDataRepository(pokemonListImplementation)

    private lateinit var listPokemons : List<PokemonResult>
    private val _pokemonsApiResult = MutableLiveData<List<PokemonResult>>()
    val pokemonList : LiveData<List<PokemonResult>>?
        get() = _pokemonsApiResult

    fun init(){
        getPokemons()
    }

    private fun getPokemons(){
        Thread{
            val pokemonAllList = pokemonListDataRepository.getAllPokemonLimit()
            _pokemonsApiResult.postValue(pokemonAllList.results)
            listPokemons = pokemonAllList.results
        }.start()
    }


}