package com.example.examenalejandromarino.ui.pantallas.equipos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examenalejandromarino.R
import com.example.examenalejandromarino.databinding.ItemEquipoBinding
import com.example.examenalejandromarino.domain.modelo.Equipo

class EquiposAdapter(val actions: EquipoActions) :
    ListAdapter<Equipo, EquiposAdapter.EquiposViewHolder>(DiffCallBack()) {

    interface EquipoActions {
        fun onClickDelete(equipo: Equipo)
        fun onClickWatch(equipo: Equipo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquiposViewHolder {
        return EquiposViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_equipo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EquiposViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    class DiffCallBack : DiffUtil.ItemCallback<Equipo>() {
        override fun areItemsTheSame(oldItem: Equipo, newItem: Equipo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Equipo, newItem: Equipo): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class EquiposViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemEquipoBinding.bind(view)

        fun bind(
            equipo: Equipo
        ) {
            with(binding) {
                textView.text = equipo.nombre
                puestoView.text = equipo.puesto.toString()

                iconButtonDelete.setOnClickListener {
                    actions.onClickDelete(equipo)
                }

                item.setOnClickListener {
                    actions.onClickWatch(equipo)
                }
            }
        }
    }
}