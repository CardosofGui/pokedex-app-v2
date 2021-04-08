package com.example.pokemonfinder.model

enum class PokemonGenerationEnum(val geracao : Int, val firstPokemon : Int, val lastPokemon : Int, val texto : String, val limite : Int) {
    GERACAO_ALL(0, 1, 898, "Todos Pokemons", 898),
    GERACAO_UM(1, 1, 151, "Primeira Geração", 151),
    GERACAO_DOIS(2, 152, 251, "Segunda Geração", 99),
    GERACAO_TRES(3, 252, 386, "Terceira Geração", 134),
    GERACAO_QUATRO(4, 387, 493, "Quarta Geração", 106),
    GERACAO_QUINTA(5, 494, 649, "Quinta Geração", 155),
    GERACAO_SEXTA(6, 650, 721, "Sexta Geração", 71),
    GERACAO_SETIMA(7, 722, 809, "Setima Geração", 87),
    GERACAO_OITAVA(8, 810, 898, "Oitava Geração", 88)
}