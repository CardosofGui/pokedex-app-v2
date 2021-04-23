package com.example.pokemonfinder.framework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonfinder.data.PokemonListDataRepository
import com.example.pokemonfinder.domain.PokemonModel
import com.example.pokemonfinder.implementations.PokemonListImplementation

class PokemonDetailsViewModel : ViewModel() {
    private val pokemonListImplementation = PokemonListImplementation()
    private val pokemonListDataRepository = PokemonListDataRepository(pokemonListImplementation)

    private var pokemonNumber : Int = -1

    private val _pokemonDetails = MutableLiveData<PokemonModel>()
    val pokemonDetails : LiveData<PokemonModel>
        get() = _pokemonDetails

    fun init(pokeNumber : Int) {
        setPokemonNumber(pokeNumber)
        pokemonDetails()
    }

    private fun setPokemonNumber(number : Int){
        this.pokemonNumber = number
    }

    private fun pokemonDetails(){
        Thread{
            val pokemonDetailsGet = pokemonListDataRepository.getPokemon(pokemonNumber.toString())
            _pokemonDetails.postValue(pokemonDetailsGet)
        }.start()
    }
}