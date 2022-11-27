package com.moviles.appf1teams.ui.pantalla.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moviles.appf1teams.R
import com.moviles.appf1teams.databinding.ActivityMainBinding
import com.moviles.appf1teams.domain.modelo.Team
import com.moviles.appf1teams.ui.common.Constantes
import com.moviles.appf1teams.ui.pantalla.drivers.DriversActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra(Constantes.id, Constantes.NotFound)

        viewModel.handleEvent(MainEvent.LoadTeam(id))

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
                if (viewModel.uiState.value?.team?.id == 0) {
                    verDrivers?.visibility = View.INVISIBLE
                    drivers?.visibility = View.INVISIBLE
                } else {
                    verDrivers?.visibility = View.VISIBLE
                    drivers?.visibility = View.VISIBLE
                }
            }
            floatingActionButton()
            bottomNav()

            verDrivers?.setOnClickListener {
                val intent = Intent(this@MainActivity, DriversActivity::class.java)
                intent.putExtra(Constantes.id, viewModel.uiState.value?.team?.id)
                startActivity(intent)
            }
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
                                name = textName.text.toString(),
                                performance = slider.value,
                                tyre = num,
                                winner = switchMaterial.isChecked
                            )
                        )
                    )
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.attrRequired,
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
                                resources.getString(R.string.attrRequired),
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
