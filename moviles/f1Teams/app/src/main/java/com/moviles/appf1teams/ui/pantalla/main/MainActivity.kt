package com.moviles.appf1teams.ui.pantalla.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moviles.appf1teams.R
import com.moviles.appf1teams.data.TeamRepository
import com.moviles.appf1teams.data.TeamsRoomDatabase
import com.moviles.appf1teams.databinding.ActivityMainBinding
import com.moviles.appf1teams.domain.modelo.Team
import com.moviles.appf1teams.domain.usecases.teams.AddTeam
import com.moviles.appf1teams.domain.usecases.teams.Delete
import com.moviles.appf1teams.domain.usecases.teams.GetTeams
import com.moviles.appf1teams.domain.usecases.teams.Update
import com.moviles.appf1teams.ui.common.Constantes
import com.moviles.appf1teams.utils.StringProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//pasar id al cambiar de pantalla
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AddTeam(TeamRepository(TeamsRoomDatabase.getDatabase(this).teamDao())),
            Delete(TeamRepository(TeamsRoomDatabase.getDatabase(this).teamDao())),
            Update(TeamRepository(TeamsRoomDatabase.getDatabase(this).teamDao())),
            GetTeams(TeamRepository(TeamsRoomDatabase.getDatabase(this).teamDao())),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {
            val team = it.getParcelable<Team>(Constantes.team)
            if (team != null) {
                viewModel.handleEvent(MainEvent.LoadTeam(team))
            } else
                viewModel.handleEvent(MainEvent.LoadTeam(Team()))
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)

            viewModel.uiState.observe(this@MainActivity) { state ->
                state.message?.let { error ->
                    Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                }
                if (state.message == null) {
                    textName.setText(state.team.name)
                    slider.value = state.team.performance
                    when (state.team.tyre) {
                        Constantes.IntSoft -> {
                            toggleButton.check(R.id.buttonS)
                        }
                        Constantes.IntMedium -> {
                            toggleButton.check(R.id.buttonM)
                        }
                        Constantes.IntHard -> {
                            toggleButton.check(R.id.buttonH)
                        }
                        Constantes.Zero -> {
                            toggleButton.clearChecked()
                        }
                    }
                    switchMaterial.isChecked = state.team.winner
                }
            }
            floatingActionButton()
            bottomNav()
        }
    }

    private fun floatingActionButton() {
        with(binding) {
            floatingActionButton.setOnClickListener {
                if (textName.text.toString() != Constantes.EmptyText && toggleButton.checkedButtonId != Constantes.NotFound) {
                    val num = getToggleButton()
                    viewModel.handleEvent(
                        MainEvent.AddTeam(
                            Team(
                                name= textName.text.toString(),
                                performance = slider.value,
                                tyre = num,
                                winner = switchMaterial.isChecked
                            )
                        )
                    )
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        StringProvider.instance(this@MainActivity).getString(R.string.attrRequired),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun bottomNav() {
        with(binding) {
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.itemLeft -> {
                        viewModel.handleEvent(MainEvent.PreviousTeam)
                        true
                    }
                    R.id.itemRight -> {
                        viewModel.handleEvent(MainEvent.NextTeam)
                        true
                    }
                    R.id.itemUpdate -> {
                        if (textName.text.toString() != Constantes.EmptyText && toggleButton.checkedButtonId != Constantes.NotFound) {
                            val num = getToggleButton()
                            viewModel.handleEvent(
                                MainEvent.UpdateTeam(
                                    textName.text.toString(),
                                    slider.value,
                                    num,
                                    switchMaterial.isChecked
                                )
                            )
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                StringProvider.instance(this@MainActivity)
                                    .getString(R.string.attrRequired),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        true
                    }
                    R.id.itemDelete -> {
                        viewModel.handleEvent(MainEvent.DeleteTeam)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }

    private fun getToggleButton(): Int {
        with(binding) {
            when (toggleButton.checkedButtonId) {
                R.id.buttonS -> {
                    return Constantes.IntSoft
                }
                R.id.buttonM -> {
                    return Constantes.IntMedium
                }
                R.id.buttonH -> {
                    return Constantes.IntHard
                }
                else -> {
                    return Constantes.Zero
                }
            }
        }
    }


}
