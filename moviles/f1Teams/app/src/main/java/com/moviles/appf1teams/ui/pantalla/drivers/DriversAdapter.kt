package com.moviles.appf1teams.ui.pantalla.drivers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviles.appf1teams.R
import com.moviles.appf1teams.databinding.ItemDriverBinding
import com.moviles.appf1teams.domain.modelo.Driver

class DriversAdapter(val actions: DriverActions) :
    ListAdapter<Driver, DriversAdapter.DriversViewHolder>(DiffCallback()) {

    interface DriverActions {
        fun onClickDelete(driver: Driver)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversViewHolder {
        return DriversViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_driver, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DriversViewHolder, position: Int) = with(holder) {
        val driver = getItem(position)
        bind(driver)
    }

    class DiffCallback : DiffUtil.ItemCallback<Driver>() {

        override fun areItemsTheSame(oldItem: Driver, newItem: Driver): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Driver, newItem: Driver): Boolean {
            return oldItem == newItem
        }
    }

    inner class DriversViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemDriverBinding.bind(view)

        fun bind(driver: Driver) {
            with(binding) {
                textView.text = driver.name
                numberView.text = driver.number.toString()
                iconButtonDelete.setOnClickListener {
                    actions.onClickDelete(driver)
                }
            }
        }
    }
}