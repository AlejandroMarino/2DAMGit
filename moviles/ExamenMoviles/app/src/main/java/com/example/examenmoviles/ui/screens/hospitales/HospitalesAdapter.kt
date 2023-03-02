package com.example.examenmoviles.ui.screens.hospitales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examenmoviles.R
import com.example.examenmoviles.databinding.ItemHospitalBinding
import com.example.examenmoviles.domain.modelo.Hospital

class HospitalesAdapter(val actions: HospitalesActions) :
    ListAdapter<Hospital, HospitalesAdapter.HospitalesViewHolder>(DiffCallback()) {

    interface HospitalesActions {
        fun onHospitalDelete(hospital: Hospital)
        fun onHospitalClick(hospital: Hospital)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalesViewHolder {
        return HospitalesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hospital, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HospitalesViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class HospitalesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemHospitalBinding.bind(view)

        fun bind(
            hospital: Hospital,
        ) {
            with(binding) {
                textView.text = hospital.nombre
                itemView.setOnClickListener {
                    actions.onHospitalClick(hospital)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Hospital>() {

        override fun areItemsTheSame(oldItem: Hospital, newItem: Hospital): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hospital, newItem: Hospital): Boolean {
            return oldItem == newItem
        }
    }

}