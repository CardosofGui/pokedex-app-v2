package com.example.pokemonfinder.presenter.pokemon_details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonfinder.R
import com.example.pokemonfinder.domain.IndexMoves
import kotlinx.android.synthetic.main.move_item.view.*

class MoveAdapter(
    private val context: Context,
    private val listMove: List<IndexMoves>?

) : RecyclerView.Adapter<MoveViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.move_item, parent, false)
        return MoveViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = listMove?.size ?: 0


    override fun onBindViewHolder(holder: MoveViewHolder, position: Int) {
        var move = listMove?.get(position)
        val view = holder.itemView

        if (move != null) {
            view.txtNomeMoveItem.text = move.move.name.capitalize()
        }
        if (move != null) {
            view.txtLvlMoveItem.text = "Level: ${move.version_group_details[0].level_learned_at}"
        }
    }
}

class MoveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)