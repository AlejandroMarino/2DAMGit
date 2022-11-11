package com.moviles.appf1teams.ui.pantalla.recycleView

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.moviles.appf1teams.R
import com.moviles.appf1teams.data.TeamRepository
import com.moviles.appf1teams.data.TeamsRoomDatabase
import com.moviles.appf1teams.databinding.ActivityRecycleviewBinding
import com.moviles.appf1teams.domain.modelo.Team
import com.moviles.appf1teams.domain.usecases.teams.AddTeam
import com.moviles.appf1teams.domain.usecases.teams.Delete
import com.moviles.appf1teams.domain.usecases.teams.GetTeams
import com.moviles.appf1teams.ui.common.Constantes
import com.moviles.appf1teams.ui.pantalla.main.MainActivity
import com.moviles.appf1teams.utils.StringProvider


class RecycleViewActivity : AppCompatActivity() {
    private lateinit var adapter: TeamsAdapter

    private lateinit var binding: ActivityRecycleviewBinding

//no crear el adapter cada vez que cambia un valor del recycler view hacerlo nuevo
    private val viewModel: RecycleViewViewModel by viewModels {
        RecycleViewViewModelFactory(
            StringProvider.instance(this),
            GetTeams(TeamRepository(TeamsRoomDatabase.getDatabase(this).teamDao())),
            Delete(TeamRepository(TeamsRoomDatabase.getDatabase(this).teamDao())),
            AddTeam(TeamRepository(TeamsRoomDatabase.getDatabase(this).teamDao())),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleviewBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)
            adapter = TeamsAdapter(object : TeamsAdapter.TeamActions{
                override fun onClickWatch(team: Team) {
                    val intent = Intent(this@RecycleViewActivity, MainActivity::class.java)
                    intent.putExtra(Constantes.team, team)
                    startActivity(intent)
                }
                override fun onClickDelete(team: Team) {
                    viewModel.handleEvent(RecycleViewEvent.DeleteTeam(team))
                    Snackbar.make(binding.root, R.string.team_Deleted, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo) {
                            viewModel.handleEvent(RecycleViewEvent.AddTeam(team))
                        }
                        .setBackgroundTint(resources.getColor(R.color.black))
                        .setTextColor(resources.getColor(R.color.white))
                        .setActionTextColor(resources.getColor(R.color.purple_700))
                        .show()
                }
            })
            list.adapter = adapter
            list.setHasFixedSize(true)

            img!!.load(Uri.parse(Constantes.image))

            viewModel.uiState.observe(this@RecycleViewActivity) { state ->
                state.error?.let { error ->
                    Toast.makeText(this@RecycleViewActivity, error, Toast.LENGTH_SHORT).show()
                }
                if (state.error == null) {
                    adapter.submitList(state.teams)
                }
            }

            button.setOnClickListener {
                val intent = Intent(this@RecycleViewActivity, MainActivity::class.java)
                intent.putExtra(Constantes.team, Team())
                startActivity(intent)
            }
        }
    }

}