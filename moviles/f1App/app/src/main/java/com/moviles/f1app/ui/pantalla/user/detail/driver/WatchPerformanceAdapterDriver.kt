package com.moviles.f1app.ui.pantalla.user.detail.driver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviles.f1app.R
import com.moviles.f1app.databinding.ItemPerformanceBinding
import com.moviles.f1app.domain.modelo.Performance
import com.moviles.f1app.domain.modelo.PerformanceWithObjects

class WatchPerformanceAdapterDriver(val actions: PerformanceActions) :
    ListAdapter<PerformanceWithObjects, WatchPerformanceAdapterDriver.PerformanceViewHolder>(DiffCallback()) {

    interface PerformanceActions {
        fun onClickWatch(performance: PerformanceWithObjects)
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
            performance: PerformanceWithObjects,
        ) {
            with(binding) {
                name.text = performance.race.track
                textPosition.text = performance.position.toString()
                textLap.text = performance.fastestLap

                itemView.setOnClickListener {
                    actions.onClickWatch(performance)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PerformanceWithObjects>() {

        override fun areItemsTheSame(oldItem: PerformanceWithObjects, newItem: PerformanceWithObjects): Boolean {
            return oldItem.driver == newItem.driver && oldItem.race == newItem.race
        }

        override fun areContentsTheSame(oldItem: PerformanceWithObjects, newItem: PerformanceWithObjects): Boolean {
            return oldItem == newItem
        }
    }
}