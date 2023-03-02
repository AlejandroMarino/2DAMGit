package com.example.examenmoviles.ui.screens.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examenmoviles.R
import com.example.examenmoviles.databinding.ItemPacienteBinding
import com.example.examenmoviles.domain.modelo.Paciente

class PacientesAdapter(val actions: PacientesActions) :
    ListAdapter<Paciente, PacientesAdapter.PacientesViewHolder>(DiffCallback()) {

    interface PacientesActions {
        fun onPacienteClick(paciente: Paciente)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacientesViewHolder {
        return PacientesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hospital, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PacientesViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class PacientesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemPacienteBinding.bind(view)

        fun bind(
            paciente: Paciente,
        ) {
            with(binding) {
                textView.text = paciente.nombre

                itemView.setOnClickListener {
                    actions.onPacienteClick(paciente)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Paciente>() {

        override fun areItemsTheSame(oldItem: Paciente, newItem: Paciente): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Paciente, newItem: Paciente): Boolean {
            return oldItem == newItem
        }
    }

}