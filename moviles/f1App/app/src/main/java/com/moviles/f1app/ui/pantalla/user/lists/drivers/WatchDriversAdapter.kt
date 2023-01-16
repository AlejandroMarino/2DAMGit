package com.moviles.f1app.ui.pantalla.user.lists.drivers

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviles.f1app.R
import com.moviles.f1app.databinding.ItemWithFotoBinding
import com.moviles.f1app.domain.modelo.Driver

class WatchDriversAdapter(val actions: WatchDriversAdapter.DriversActions) :
    ListAdapter<Driver, WatchDriversAdapter.DriversViewHolder>(DiffCallback()) {

    interface DriversActions {
        fun onDriverWatch(driver: Driver)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversViewHolder {
        return DriversViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_with_foto, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DriversViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class DriversViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemWithFotoBinding.bind(view)

        fun bind(
            driver: Driver,
        ) {
            with(binding) {
                if (driver.photo != "") {
                    driverPhoto.setImageURI(Uri.parse(driver.photo))
                } else {
                    driverPhoto.setImageResource(R.drawable.ic_person_24)
                }
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