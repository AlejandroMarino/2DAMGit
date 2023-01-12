package com.moviles.f1app.ui.pantalla.admin.detail.race

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviles.f1app.R
import com.moviles.f1app.databinding.ItemPerformanceBinding
import com.moviles.f1app.domain.modelo.Performance

class PerformanceAdapterRace(val actions: PerformanceActions) :
    ListAdapter<Performance, PerformanceAdapterRace.PerformanceViewHolder>(DiffCallback()) {

    interface PerformanceActions {
        fun onClickWatch(performance: Performance)
        fun onClickDelete(performance: Performance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformanceViewHolder {
        return PerformanceViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_performance, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PerformanceViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class PerformanceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemPerformanceBinding.bind(view)

        fun bind(
            performance: Performance,
        ) {
            with(binding) {
                name.text = performance.idDriver.toString()
                textPosition.text = performance.position.toString()
                textLap.text = performance.fastestLap

                itemView.setOnClickListener {
                    actions.onClickWatch(performance)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Performance>() {

        override fun areItemsTheSame(oldItem: Performance, newItem: Performance): Boolean {
            return oldItem.idDriver == newItem.idDriver && oldItem.idRace == newItem.idRace
        }

        override fun areContentsTheSame(oldItem: Performance, newItem: Performance): Boolean {
            return oldItem == newItem
        }
    }
}