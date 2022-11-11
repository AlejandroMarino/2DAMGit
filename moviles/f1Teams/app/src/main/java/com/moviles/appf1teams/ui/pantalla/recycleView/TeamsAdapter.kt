package com.moviles.appf1teams.ui.pantalla.recycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviles.appf1teams.R
import com.moviles.appf1teams.databinding.ItemTeamBinding
import com.moviles.appf1teams.domain.modelo.Team

class TeamsAdapter(val actions: TeamActions) :
    ListAdapter<Team, TeamsAdapter.TeamsViewHolder>(DiffCallback()) {

    interface TeamActions {
        fun onClickWatch(team: Team)
        fun onClickDelete(team: Team)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_team, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class TeamsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemTeamBinding.bind(view)

        fun bind(
            team: Team,
        ) {
            with(binding) {
                textView.text = team.name


                iconButtonWatch.setOnClickListener {
                    actions.onClickWatch(team)
                }

                iconButtonDelete.setOnClickListener {
                    actions.onClickDelete(team)
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