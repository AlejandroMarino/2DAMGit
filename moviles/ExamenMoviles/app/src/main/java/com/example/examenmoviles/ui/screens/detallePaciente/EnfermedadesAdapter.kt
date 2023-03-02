package com.example.examenmoviles.ui.screens.detallePaciente

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examenmoviles.R
import com.example.examenmoviles.databinding.ItemEnfermedadBinding
import com.example.examenmoviles.domain.modelo.Enfermedad

class EnfermedadesAdapter() :
    ListAdapter<Enfermedad, EnfermedadesAdapter.EnfermedadesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnfermedadesViewHolder {
        return EnfermedadesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hospital, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EnfermedadesViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class EnfermedadesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemEnfermedadBinding.bind(view)

        fun bind(
            enfermedad: Enfermedad,
        ) {
            with(binding) {
                textView.text = enfermedad.nombre
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Enfermedad>() {

        override fun areItemsTheSame(oldItem: Enfermedad, newItem: Enfermedad): Boolean {
            return oldItem.nombre == newItem.nombre
        }

        override fun areContentsTheSame(oldItem: Enfermedad, newItem: Enfermedad): Boolean {
            return oldItem == newItem
        }
    }

}