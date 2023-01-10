package com.moviles.f1app.ui.pantalla.admin.list.teams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviles.f1app.R
import com.moviles.f1app.databinding.ItemBinding
import com.moviles.f1app.domain.modelo.Team

class TeamsAdapter(val actions: TeamsActions) :
    ListAdapter<Team, TeamsAdapter.TeamsViewHolder>(DiffCallback()) {

    interface TeamsActions {
        fun onTeamDelete(team: Team)
        fun onTeamWatch(team: Team)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class TeamsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemBinding.bind(view)

        fun bind(
            team: Team,
        ) {
            with(binding) {
                textView.text = team.name

                itemView.setOnClickListener {
                    actions.onTeamWatch(team)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Team>() {

        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem == newItem
        }
    }

}