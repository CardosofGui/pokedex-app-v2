package com.example.pokemonfinder.presenter.pokemon_details

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.pokemonfinder.data.PokemonSingleton
import com.example.pokemonfinder.R
import com.example.pokemonfinder.presenter.pokemon_details.adapter.MoveAdapter
import com.example.pokemonfinder.presenter.pokemon_list.Pokemon_List.Companion.POKEMON_SELECIONADO
import com.example.pokemonfinder.domain.PokemonModel
import com.example.pokemonfinder.domain.enum_model.PokemonTypeEnum
import com.example.pokemonfinder.framework.viewmodel.PokemonDetailsViewModel
import kotlinx.android.synthetic.main.activity_pokemon__details.*


class Pokemon_Details : AppCompatActivity() {

    lateinit var adapter : MoveAdapter

    private lateinit var pokemonDetailsViewModel : PokemonDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon__details)

        pokemonDetailsViewModel = ViewModelProvider.NewInstanceFactory().create(PokemonDetailsViewModel::class.java)
        pokemonDetailsViewModel.init(intent.getIntExtra(POKEMON_SELECIONADO, 0))

        initObserver()
        initClicks()
    }

    private fun initObserver() {
        pokemonDetailsViewModel.pokemonDetails.observe(this, Observer {pokemonSelecionado ->
            if(pokemonSelecionado != null){
                hideLoading()

                txtHabilidadesPokemonDetails.text = pokemonSelecionado.getAbilities()
                txtNomePokemonDetails.text = pokemonSelecionado.name
                txtNomePokemonDetails.text = pokemonSelecionado.name?.capitalize()
                txtAlturaPokemonDetails.text = pokemonSelecionado.height!!.toString() + "m"
                txtIdPokemonDetails.text = pokemonSelecionado.formatPokemonID()
                txtPesoPokemonDetails.text = pokemonSelecionado.weight!!.toString() + "kg"
                Glide.with(this).load(pokemonSelecionado.getImage()).into(imgPokemonDetails)

                pokemonSelecionado.getStrongest().forEach { createTextViewType(it, llnVantagens) }
                pokemonSelecionado.getWeaknesses().forEach { createTextViewType(it, llnFraquezas) }

                val typePrimary = pokemonSelecionado.types?.get(0)?.type?.name
                txtType1PokemonDetails.text = typePrimary?.capitalize()
                txtType1PokemonDetails.setBackgroundColor(getColor(pokemonSelecionado.getColor(typePrimary)))
                window.statusBarColor = getColor(pokemonSelecionado.getColor(typePrimary))

                if(pokemonSelecionado.types?.size == 2){
                    val typeSecondary = pokemonSelecionado.types[1].type.name
                    txtType2PokemonDetails.visibility = View.VISIBLE
                    txtType2PokemonDetails.text = typeSecondary?.capitalize()
                    txtType2PokemonDetails.setBackgroundColor(getColor(pokemonSelecionado.getColor(typeSecondary)))
                }

                initRecycler(pokemonSelecionado)
            }else{
                showLoading()
            }
        })
    }

    private fun initClicks() {
        btnClosePokemonDetails.setOnClickListener {
            onBackPressed()
            this.finish()
        }
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

    private fun initRecycler(pokemonSelecionado : PokemonModel) {
        val listMoves =
            pokemonSelecionado.moves?.filter { it.version_group_details[0].level_learned_at > 0 }
                ?.sortedBy { it.version_group_details[0].level_learned_at }

        adapter = MoveAdapter(this, listMoves)
        recyclerViewMoves.isNestedScrollingEnabled = false
        recyclerViewMoves.adapter = adapter
        recyclerViewMoves.layoutManager = GridLayoutManager(this, 3)
    }

    private fun hideLoading(){
        pokemonInformation.visibility = View.VISIBLE
        llnLoadingPokemonDetails.visibility = View.GONE
    }

    private fun showLoading(){
        pokemonInformation.visibility = View.GONE
        llnLoadingPokemonDetails.visibility = View.VISIBLE
    }
}