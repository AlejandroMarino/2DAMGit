package com.moviles.appf1teams.ui.pantalla.recycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moviles.appf1teams.R
import com.moviles.appf1teams.databinding.ItemTeamBinding
import com.moviles.appf1teams.domain.modelo.Team

class TeamsAdapter(
    private var teams: List<Team>,
    private val onClickWatch: (Team) -> Unit,
    private val onClickDelete: (Team) -> Unit,

    ) : RecyclerView.Adapter<TeamsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TeamsViewHolder(layoutInflater.inflate(R.layout.item_team, parent, false))
    }


    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.render(teams[position], onClickWatch, onClickDelete)
    }

    override fun getItemCount(): Int = teams.size

}

class TeamsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemTeamBinding.bind(view)

    fun render(
        team: Team,
        onClickWatch: (Team) -> Unit,
        onClickDelete: (Team) -> Unit
    ) {
        with(binding) {
            textView.text = team.name

            iconButtonWatch.setOnClickListener {
                onClickWatch(team)
            }

            iconButtonDelete.setOnClickListener {
                onClickDelete(team)
            }
        }
    }
}