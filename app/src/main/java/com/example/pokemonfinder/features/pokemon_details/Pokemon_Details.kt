package com.example.pokemonfinder.features.pokemon_details

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.pokemonfinder.PokemonSingleton
import com.example.pokemonfinder.R
import com.example.pokemonfinder.features.pokemon_details.adapter.MoveAdapter
import com.example.pokemonfinder.features.pokemon_list.Pokemon_List.Companion.POKEMON_SELECIONADO
import com.example.pokemonfinder.model.PokemonModel
import com.example.pokemonfinder.model.PokemonTypeEnum
import kotlinx.android.synthetic.main.activity_pokemon__details.*


class Pokemon_Details : AppCompatActivity() {

    lateinit var adapter : MoveAdapter

    private lateinit var pokemonSelecionado : PokemonModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon__details)

        initPokemonSelecionado()
        initRecycler()
        initDados()
        initClicks()
    }

    private fun initClicks() {
        btnClosePokemonDetails.setOnClickListener {
            onBackPressed()
            this.finish()
        }
    }

    @SuppressLint("CheckResult")
    private fun initDados() {
        var habilidades : String = ""
        val fraquezas : ArrayList<String> = arrayListOf()
        val vantagens : ArrayList<String> = arrayListOf()

        pokemonSelecionado.abilities?.forEach {
            habilidades += it.ability.name.capitalize() + ", "
        }

        PokemonTypeEnum.values().forEach {
            if(it.typeName == pokemonSelecionado.types?.get(0)?.type?.name){
                txtType1PokemonDetails.text = pokemonSelecionado.types?.get(0)?.type?.name?.capitalize()
                txtType1PokemonDetails.setBackgroundColor(ContextCompat.getColor(this, it.typeColor))
                viewColorType.setBackgroundColor(ContextCompat.getColor(this, it.typeColor))
                window.statusBarColor = ContextCompat.getColor(this, it.typeColor)
                fraquezas.addAll(it.fracoContra)
                vantagens.addAll(it.forteContra)
            }
        }
        if(pokemonSelecionado.types?.size == 2){
            txtType2PokemonDetails.visibility = View.VISIBLE
            PokemonTypeEnum.values().forEach {
                if(it.typeName == pokemonSelecionado.types?.get(1)?.type?.name){
                    txtType2PokemonDetails.text = pokemonSelecionado.types?.get(1)?.type?.name?.capitalize()
                    txtType2PokemonDetails.setBackgroundColor(ContextCompat.getColor(this, it.typeColor))
                    fraquezas.addAll(it.fracoContra)
                    vantagens.addAll(it.forteContra)
                }
            }
        }

        txtNomePokemonDetails.text = pokemonSelecionado.name?.capitalize()
        txtAlturaPokemonDetails.text = pokemonSelecionado.height!!.toString() + "m"
        txtIdPokemonDetails.text = pokemonSelecionado.formatPokemonID()
        txtPesoPokemonDetails.text = pokemonSelecionado.weight!!.toString() + "kg"
        txtHabilidadesPokemonDetails.text = habilidades.substring(0, habilidades.length-2)
        Glide.with(this).load(pokemonSelecionado.sprites!!.front_default).into(imgPokemonDetails)

        fraquezas.distinct().forEach {
            createTextViewType(it, llnFraquezas)
        }
        vantagens.distinct().forEach {
            createTextViewType(it, llnVantagens)
        }
    }

    private fun initPokemonSelecionado() {
        val index = intent.getIntExtra(POKEMON_SELECIONADO, 0)
        pokemonSelecionado = PokemonSingleton.pokemonList[index]
    }

    private fun createTextViewType(type : String, linearLayout: LinearLayout){
        val textView = TextView(this)
        textView.text = type.capitalize()
        textView.setPadding(20, 5, 20, 5)
        textView.typeface = Typeface.DEFAULT_BOLD
        textView.gravity = Gravity.CENTER
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        params.setMargins(0, 0, 10, 0)
        textView.layoutParams = params

        PokemonTypeEnum.values().forEach {
            if(it.typeName.capitalize() == type.capitalize()){
                textView.setBackgroundColor(ContextCompat.getColor(this, it.typeColor))
            }
        }

        linearLayout.addView(textView)
    }



    private fun initRecycler() {
        val listMoves =
            pokemonSelecionado.moves?.filter { it.version_group_details[0].level_learned_at > 0 }
                ?.sortedBy { it.version_group_details[0].level_learned_at }

        adapter = MoveAdapter(this, listMoves)
        recyclerViewMoves.isNestedScrollingEnabled = false
        recyclerViewMoves.adapter = adapter
        recyclerViewMoves.layoutManager = GridLayoutManager(this, 3)
    }
}