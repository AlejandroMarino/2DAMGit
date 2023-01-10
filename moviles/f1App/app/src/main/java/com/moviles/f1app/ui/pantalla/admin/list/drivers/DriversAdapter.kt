package com.moviles.f1app.ui.pantalla.admin.list.drivers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviles.f1app.R
import com.moviles.f1app.databinding.ItemBinding
import com.moviles.f1app.domain.modelo.Driver
import com.moviles.f1app.domain.modelo.Team
import com.moviles.f1app.ui.pantalla.admin.list.races.RacesAdapter

class DriversAdapter(val actions: DriversAdapter.DriversActions) :
    ListAdapter<Driver, DriversAdapter.DriversViewHolder>(DiffCallback()) {

    interface DriversActions {
        fun onDriverDelete(driver: Driver)
        fun onDriverWatch(driver: Driver)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversViewHolder {
        return DriversViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DriversViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class DriversViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemBinding.bind(view)

        fun bind(
            driver: Driver,
        ) {
            with(binding) {
                textView.text = driver.name

                itemView.setOnClickListener {
                    actions.onDriverWatch(driver)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Driver>() {

        override fun areItemsTheSame(oldItem: Driver, newItem: Driver): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Driver, newItem: Driver): Boolean {
            return oldItem == newItem
        }
    }
}