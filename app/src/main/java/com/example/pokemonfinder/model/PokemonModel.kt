package com.example.pokemonfinder.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PokemonModel(
    val id: Int?,
    val name : String?,
    val types : List<index>?,
    val sprites: sprites?,
    val moves : List<IndexMoves>?,
    val abilities : List<Ability>?,
    val weight : Int?,
    val height : Int?
){

    var position = 0

    fun setPositionPoke(index : Int){
        position = index
    }

    fun formatPokemonID() : String{
        if(this.id != null){
            if(this.id < 10){
                return "#00"+this.id
            }else if(this.id < 100){
                return "#0"+this.id
            }else{
                return "#"+this.id
            }
        }else{
            return "Null"
        }
    }
}

class Ability(
    val ability : Ability__1
)

class Ability__1(
    val name : String
)

class IndexMoves(val move : Move, val version_group_details : List<IndexLevel>)
class Move(val name : String)
class IndexLevel(val level_learned_at : Int)
class index(val type : type)
class type(val name : String)
class sprites(val front_default : String)
