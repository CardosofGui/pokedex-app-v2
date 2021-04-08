package com.example.pokemonfinder.model

import com.example.pokemonfinder.R

enum class PokemonTypeEnum(
    val typeName: String,
    val typeColor: Int,
    val forteContra: Array<String>,
    val fracoContra: Array<String>
) {
    bug(
        "bug",
        R.color.bug,
        arrayOf("grass", "psychic", "dark"),
        arrayOf("fighting", "flying", "poison", "ghost", "steel", "fire", "fairy")
    ),
    dark("dark", R.color.dark, arrayOf("ghost", "psychic"), arrayOf("fighting", "dark", "fairy")),
    dragon("dragon", R.color.dragon, arrayOf("dragon"), arrayOf("steel", "fairy")),
    eletric(
        "electric",
        R.color.electric,
        arrayOf("flying", "water"),
        arrayOf("ground", "grass", "electric", "dragon")
    ),
    fairy(
        "fairy",
        R.color.fairy,
        arrayOf("fighting", "dragon", "dark"),
        arrayOf("poison", "steel", "fire")
    ),
    fight(
        "fighting",
        R.color.fighting,
        arrayOf("normal", "rock", "steel", "ice", "dark"),
        arrayOf("Flying", "Poison", "Psychic", "Bug", "Ghost", "Fairy")
    ),
    fire(
        "fire",
        R.color.fire,
        arrayOf("Bug", "Steel", "Grass", "Ice"),
        arrayOf("Rock", "Fire", "Water", "Dragon")
    ),
    flying(
        "flying",
        R.color.flying,
        arrayOf("Fighting", "Bug", "Grass"),
        arrayOf("Rock", "Steel", "Electric")
    ),
    ghost("ghost", R.color.ghost, arrayOf("Ghost", "Psychic"), arrayOf("Normal", "Dark")),
    grass(
        "grass",
        R.color.grass,
        arrayOf("Ground", "Rock", "Water"),
        arrayOf("Flying", "Poison", "Bug", "Steel", "Fire", "Grass", "Dragon")
    ),
    ground(
        "ground",
        R.color.ground,
        arrayOf("Poison", "Rock", "Steel", "Fire", "Electric"),
        arrayOf("Flying", "Bug", "Grass")
    ),
    ice(
        "ice",
        R.color.ice,
        arrayOf("Flying", "Ground", "Grass", "Dragon"),
        arrayOf("Steel", "Fire", "Water", "Ice")
    ),
    normal("normal", R.color.normal, arrayOf(), arrayOf("Rock", "Ghost", "Steel")),
    poison(
        "poison",
        R.color.poison,
        arrayOf("Grass", "Fairy"),
        arrayOf("Poison", "Ground", "Rock", "Ghost", "Steel")
    ),
    psychic(
        "psychic",
        R.color.psychic,
        arrayOf("Fighting", "Poison"),
        arrayOf("Steel", "Psychic", "Dark")
    ),
    rock(
        "rock",
        R.color.rock,
        arrayOf("Flying", "Bug", "Fire", "Ice"),
        arrayOf("Fighting", "Ground", "Steel")
    ),
    steel(
        "steel",
        R.color.steel,
        arrayOf("Rock", "Ice", "Fairy"),
        arrayOf("Steel", "Fire", "Water", "Electric")
    ),
    water(
        "water",
        R.color.water,
        arrayOf("Ground", "Rock", "Fire"),
        arrayOf("Water", "Grass", "Dragon")
    )
}