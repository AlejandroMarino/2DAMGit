package com.moviles.f1app.ui.pantalla.user.detail.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviles.f1app.R
import com.moviles.f1app.databinding.ItemDriverBinding
import com.moviles.f1app.domain.modelo.Driver

class DriversAdapter(val actions: DriversActions) :
    ListAdapter<Driver, DriversAdapter.DriversViewHolder>(DiffCallback()) {

    interface DriversActions {
        fun onClickWatch(driver: Driver)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversViewHolder {
        return DriversViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_driver, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DriversViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class DriversViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemDriverBinding.bind(view)

        fun bind(
            driver: Driver,
        ) {
            with(binding) {
                name.text = driver.name
                textNumber.text = driver.number.toString()

                itemView.setOnClickListener {
                    actions.onClickWatch(driver)
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