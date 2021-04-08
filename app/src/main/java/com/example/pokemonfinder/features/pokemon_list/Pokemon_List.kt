package com.example.pokemonfinder.features.pokemon_list

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonfinder.PokemonSingleton
import com.example.pokemonfinder.R
import com.example.pokemonfinder.api.RetrofitPokemon
import com.example.pokemonfinder.features.pokemon_details.Pokemon_Details
import com.example.pokemonfinder.features.pokemon_list.adapter.PokemonAdapter
import com.example.pokemonfinder.model.PokemonClickAction
import com.example.pokemonfinder.model.PokemonGenerationEnum
import com.example.pokemonfinder.model.PokemonModel
import com.example.pokemonfinder.model.PokemonTypeEnum
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class Pokemon_List : AppCompatActivity(), PokemonClickAction {

    private var doubleBackToExitPressedOnce = false

    lateinit var adapter : PokemonAdapter

    var listPokemonFilter : List<PokemonModel> = ArrayList()
    var listPokemonAtual : List<PokemonModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
        initClick()
        initAutoSearch()
    }

    private fun getGeneration(firstPokemon : Int, lastPokemon : Int) {
        Log.w("Inicio", "Carregando")
        PokemonSingleton.pokemonList.clear()
        llnLoadingPokemons.visibility = View.VISIBLE

        for (i in firstPokemon..lastPokemon) {
            getData(i.toString())
        }
    }

    private fun initAutoSearch() {
        edtSearchList.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pokemonFilter = edtSearchList.text.toString()

                listPokemonFilter = if(pokemonFilter.isNotEmpty()){
                    listPokemonAtual.filter {
                        it.name!!.toLowerCase().contains(pokemonFilter.toLowerCase()) || it.id.toString().contains(pokemonFilter)
                    }
                }else{
                    listPokemonAtual
                }

                adapter.filterList(listPokemonFilter)
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
        if(PokemonSingleton.pokemonList.isEmpty()){
            recyclerViewPokemons.visibility = View.GONE
            getGeneration(1, 898)
        }

        PokemonGenerationEnum.values().forEach {
            createTextViewGeneration(it, llnGenerations)
        }

        PokemonTypeEnum.values().forEach {
            createTextViewType(it.typeName, llnTypes)
        }

        adapter =
            PokemonAdapter(
                this,
                listPokemonAtual,
                this
            )
        recyclerViewPokemons.adapter = adapter
        recyclerViewPokemons.layoutManager = GridLayoutManager(this, 3)
    }

    private fun getData(getPokemon : String) {
        val call : Call<PokemonModel> =
            RetrofitPokemon.instance?.pokemonAPI()?.getPokemonByDexNumOrName(getPokemon) as Call<PokemonModel>

        call.enqueue(object : Callback<PokemonModel>{
            override fun onFailure(call: Call<PokemonModel>, t: Throwable) {
                val pokemonNull = PokemonModel(
                    getPokemon.toInt(),
                    "Erro",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                )
                PokemonSingleton.pokemonList.adicionarPokemon(pokemonNull)
            }

            override fun onResponse(call: Call<PokemonModel>, response: Response<PokemonModel>) {
                try {
                    PokemonSingleton.pokemonList.adicionarPokemon(response.body()!!)
                }catch (e : Exception){
                    val pokemonNull = PokemonModel(
                        getPokemon.toInt(),
                        "Erro",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                    PokemonSingleton.pokemonList.adicionarPokemon(pokemonNull)

                    Log.w("Erro", "$e")
                }
            }
        })
    }

    fun MutableList<PokemonModel>.adicionarPokemon(pokemonModel: PokemonModel){
        txtLoadingPokemons.text = "${this.size}/${PokemonSingleton.limitPokemon}"
        this.add(pokemonModel)

        if(this.size == PokemonSingleton.limitPokemon){
            this.sortBy { it.id }
            this.forEach {
                it.setPositionPoke(indexOf(it))
            }

            recyclerViewPokemons.visibility = View.VISIBLE
            llnLoadingPokemons.visibility = View.GONE
            listPokemonAtual = this
            adapter.filterList(listPokemonAtual)
        }
    }

    private fun createTextViewType(type : String, linearLayout: LinearLayout){
        val textView = Button(this)
        textView.text = type.capitalize()
        textView.setPadding(35, 10, 35, 10)
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
                textView.backgroundTintList = resources.getColorStateList(it.typeColor)
            }
        }

        textView.setOnClickListener {
            textView.animation = AnimationUtils.loadAnimation(this, R.anim.animation_click)
            filterType(type)
        }


        linearLayout.addView(textView)
    }
    private fun createTextViewGeneration(pokemonGenerationEnum: PokemonGenerationEnum, linearLayout: LinearLayout){
        val textView = Button(this)
        textView.text = pokemonGenerationEnum.texto.capitalize()
        textView.setPadding(35, 10, 35, 10)
        textView.typeface = Typeface.DEFAULT_BOLD
        textView.gravity = Gravity.CENTER
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        params.setMargins(0, 0, 10, 0)
        textView.layoutParams = params
        textView.backgroundTintList = resources.getColorStateList(R.color.genColor)

        textView.setOnClickListener {
            textView.animation = AnimationUtils.loadAnimation(this, R.anim.animation_click)
            filterGeneration(pokemonGenerationEnum)
        }

        linearLayout.addView(textView)
    }
    private fun filterType(type : String){
        listPokemonFilter = listPokemonAtual.filter {
            if(it.types?.size == 2){
                it.types[0].type.name.equals(type) || it.types[1].type.name.equals(type)
            }else{
                it.types?.get(0)?.type?.name.equals(type)
            }
        }
        adapter.filterList(listPokemonFilter)

        filterGenerationType.visibility = View.GONE
        floatingFilterButton.setImageResource(R.drawable.ic_baseline_filter_alt_24)
    }
    private fun filterGeneration(pokemonGenerationEnum: PokemonGenerationEnum){
        listPokemonAtual = PokemonSingleton.pokemonList.filter { it.id!! >= pokemonGenerationEnum.firstPokemon && it.id!! <= pokemonGenerationEnum.lastPokemon }
        adapter.filterList(listPokemonAtual)

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