package com.example.pokemonfinder.presenter.pokemon_list

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonfinder.R
import com.example.pokemonfinder.presenter.pokemon_details.Pokemon_Details
import com.example.pokemonfinder.presenter.pokemon_list.adapter.PokemonAdapter
import com.example.pokemonfinder.domain.click_interface.PokemonClickAction
import com.example.pokemonfinder.domain.enum_model.PokemonGenerationEnum
import com.example.pokemonfinder.domain.enum_model.PokemonTypeEnum
import com.example.pokemonfinder.framework.viewmodel.PokemonListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class Pokemon_List : AppCompatActivity(),
    PokemonClickAction {

    private var doubleBackToExitPressedOnce = false
    private lateinit var pokemonListViewModel : PokemonListViewModel

    lateinit var adapter : PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonListViewModel = ViewModelProvider.NewInstanceFactory().create(PokemonListViewModel::class.java)
        pokemonListViewModel.init()

        initRecycler()
        initObserver()
    }

    private fun initObserver() {
        pokemonListViewModel.pokemonList?.observe(this, Observer {
            if(it != null){
                hideLoading()
                initClick()
                initDynamicFilters()
                initAutoSearch()

                adapter.setPokemonList(it)
            }else{
                showLoadingAndUpdate()
            }
        })
    }
    private fun initClick() {
        imgSearchIcon.setOnClickListener {
            imgSearchIcon.animation = AnimationUtils.loadAnimation(baseContext, R.anim.animation_click)

            if(txtTitleList.visibility == View.VISIBLE){
                txtTitleList.visibility = View.GONE
                edtSearchList.visibility = View.VISIBLE
                edtSearchList.animation = AnimationUtils.loadAnimation(baseContext, R.anim.slide_in_right)
                imgSearchIcon.setImageResource(R.drawable.ic_baseline_close_24)
            }else{
                txtTitleList.visibility = View.VISIBLE
                edtSearchList.visibility = View.GONE
                if(edtSearchList.text.isNotEmpty()) edtSearchList.setText("")
                txtTitleList.animation = AnimationUtils.loadAnimation(baseContext, R.anim.slide_in_right)
                imgSearchIcon.setImageResource(R.drawable.search_icon)
            }
        }
        floatingFilterButton.setOnClickListener {
            if(filterGenerationType.visibility == View.GONE){
                floatingFilterButton.setImageResource(R.drawable.ic_baseline_close_24)
                filterGenerationType.visibility = View.VISIBLE
                floatingFilterButton.animation = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top)
                filterGenerationType.animation = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top)
            }else{
                floatingFilterButton.setImageResource(R.drawable.ic_baseline_filter_alt_24)
                floatingFilterButton.animation = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom)
                filterGenerationType.animation = AnimationUtils.loadAnimation(baseContext, R.anim.top_to_bottom)
                filterGenerationType.visibility = View.GONE
            }
        }
    }
    private fun initRecycler() {

        adapter = PokemonAdapter(
            this,
            this
        )

        recyclerViewPokemons.adapter = adapter
        recyclerViewPokemons.layoutManager = GridLayoutManager(this, 3)
    }
    private fun initAutoSearch() {
        edtSearchList.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pokemonFilter = edtSearchList.text.toString()

                adapter.filterPokemons(searchPoke = pokemonFilter)
            }
        })
    }
    private fun initDynamicFilters(){
        PokemonGenerationEnum.values().forEach {
            createButtonGenerationFilter(it, llnGenerations)
        }
    }

    private fun hideLoading(){
        llnLoadingPokemons.visibility = View.GONE
    }
    private fun showLoadingAndUpdate(){
        llnLoadingPokemons.visibility = View.VISIBLE
    }

    private fun createButtonGenerationFilter(pokemonGenerationEnum: PokemonGenerationEnum, linearLayout: LinearLayout){
        val buttonGeneration = Button(this)
        buttonGeneration.text = pokemonGenerationEnum.texto.capitalize()
        buttonGeneration.setPadding(35, 10, 35, 10)
        buttonGeneration.typeface = Typeface.DEFAULT_BOLD
        buttonGeneration.gravity = Gravity.CENTER
        buttonGeneration.setTextColor(ContextCompat.getColor(this, R.color.white))
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        params.setMargins(0, 0, 10, 0)
        buttonGeneration.layoutParams = params
        buttonGeneration.backgroundTintList = resources.getColorStateList(R.color.genColor)

        buttonGeneration.setOnClickListener {
            buttonGeneration.animation = AnimationUtils.loadAnimation(this, R.anim.animation_click)
            adapter.filterPokemons(firstFilterGen = pokemonGenerationEnum.firstPokemon, lastFilterGen = pokemonGenerationEnum.lastPokemon)
            hideFilter()
        }

        linearLayout.addView(buttonGeneration)
    }

    private fun hideFilter() {
        filterGenerationType.visibility = View.GONE
        floatingFilterButton.setImageResource(R.drawable.ic_baseline_filter_alt_24)
    }

    override fun clickPokemon(index : Int) {
        val intent = Intent(this, Pokemon_Details::class.java)
            .putExtra(POKEMON_SELECIONADO, index)

        startActivity(intent)
    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Pressione novamente para sair", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    companion object{
        const val POKEMON_SELECIONADO = "POKEMON_SELECIONADO"
    }
}