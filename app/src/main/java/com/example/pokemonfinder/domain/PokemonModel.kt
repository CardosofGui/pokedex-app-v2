package com.example.pokemonfinder.domain

import androidx.core.content.ContextCompat
import com.example.pokemonfinder.domain.enum_model.PokemonTypeEnum
import kotlinx.android.synthetic.main.activity_pokemon__details.*

data class PokemonsApiResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
){
    fun getNumberPokemon() : Int{
        return url
            .replace("https://pokeapi.co/api/v2/pokemon/", "")
            .replace("/", "").toInt()
    }

    fun getImage() : String{
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${getNumberPokemon()}.png"
    }

    fun formatPokemonID() : String {
        return when {
            getNumberPokemon() < 10 -> {
                "#00"+getNumberPokemon()
            }
            getNumberPokemon() < 100 -> {
                "#0"+getNumberPokemon()
            }
            else -> {
                "#"+getNumberPokemon()
            }
        }
    }
}

data class PokemonModel(
    val id: Int?,
    val name : String?,
    val types : List<index>?,
    val moves : List<IndexMoves>?,
    val abilities : List<Ability>?,
    val weight : Int?,
    val height : Int?
){

    fun getAbilities(): String {
        var habilidades = ""
        abilities?.forEach { habilidades += it.ability.name.capitalize() + ", " }

        return habilidades.substring(0, habilidades.length-2)
    }

    fun getWeaknesses(): List<String> {
        val listWeaknesses = arrayListOf<String>()

        PokemonTypeEnum.values().forEach {
            if(it.typeName == types?.get(0)?.type?.name){
                listWeaknesses.addAll(it.fracoContra)
            }

            if(types?.size == 2){
                if(it.typeName == types[1].type.name){
                    listWeaknesses.addAll(it.fracoContra)
                }
            }
        }

        return listWeaknesses.distinct()
    }

    fun getStrongest(): List<String> {
        val listStrongest = arrayListOf<String>()

        PokemonTypeEnum.values().forEach {
            if(it.typeName == types?.get(0)?.type?.name){
                listStrongest.addAll(it.forteContra)
            }

            if(types?.size == 2){
                if(it.typeName == types[1].type.name){
                    listStrongest.addAll(it.fracoContra)
                }
            }
        }

        return listStrongest.distinct()
    }

    fun getColor(type : String?): Int{
        var color : Int = 0

        PokemonTypeEnum.values().forEach {
            if(it.typeName == type){
                color = it.typeColor
            }
        }

        return color
    }

    fun formatPokemonID() : String{
        return if(this.id != null){
            when {
                this.id < 10 -> {
                    "#00"+this.id
                }
                this.id < 100 -> {
                    "#0"+this.id
                }
                else -> {
                    "#"+this.id
                }
            }
        }else{
            "Null"
        }
    }

    fun getImage() : String{
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    }
}

data class Ability(
    val ability : Ability__1
)

data class Ability__1(
    val name : String
)

data class IndexMoves(val move : Move, val version_group_details : List<IndexLevel>)
data class Move(val name : String)
data class IndexLevel(val level_learned_at : Int)
data class index(val type : type)
data class type(val name : String)
