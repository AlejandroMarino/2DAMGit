package com.example.examenfinalmoviles.ui.screens.partidos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examenfinalmoviles.R
import com.example.examenfinalmoviles.databinding.ItemBinding
import com.example.examenfinalmoviles.domain.modelo.Partido

class PartidosAdapter(val actions: PartidosActions) :
    ListAdapter<Partido, PartidosAdapter.PartidosViewHolder>(DiffCallback()) {

    interface PartidosActions {
        fun onPartidoDelete(partido: Partido)
        fun onPartidoClick(partido: Partido)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartidosViewHolder {
        return PartidosViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PartidosViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class PartidosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemBinding.bind(view)

        fun bind(
            partido: Partido,
        ) {
            with(binding) {
                textView.text = partido.nombre
                itemView.setOnClickListener {
                    actions.onPartidoClick(partido)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Partido>() {

        override fun areItemsTheSame(oldItem: Partido, newItem: Partido): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Partido, newItem: Partido): Boolean {
            return oldItem == newItem
        }
    }

}