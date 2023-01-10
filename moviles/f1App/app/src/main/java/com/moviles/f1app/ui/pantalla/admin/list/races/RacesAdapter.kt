package com.moviles.f1app.ui.pantalla.admin.list.races

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviles.f1app.R
import com.moviles.f1app.databinding.ItemBinding
import com.moviles.f1app.domain.modelo.Race

class RacesAdapter(val actions: RacesActions) :
    ListAdapter<Race, RacesAdapter.RacesViewHolder>(DiffCallback()) {

    interface RacesActions {
        fun onRaceDelete(race: Race)
        fun onRaceWatch(race: Race)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RacesViewHolder {
        return RacesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RacesViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class RacesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemBinding.bind(view)

        fun bind(
            race: Race,
        ) {
            with(binding) {
                textView.text = race.track

                itemView.setOnClickListener {
                    actions.onRaceWatch(race)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Race>() {

        override fun areItemsTheSame(oldItem: Race, newItem: Race): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Race, newItem: Race): Boolean {
            return oldItem == newItem
        }
    }

}