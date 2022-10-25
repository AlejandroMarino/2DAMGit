package com.moviles.appf1teams.ui.pantallaMain

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moviles.appf1teams.R
import com.moviles.appf1teams.databinding.ActivityMainBinding
import com.moviles.appf1teams.domain.usecases.teams.AddTeam
import com.moviles.appf1teams.domain.usecases.teams.Delete
import com.moviles.appf1teams.domain.usecases.teams.GetTeams
import com.moviles.appf1teams.domain.usecases.teams.Update
import com.moviles.appf1teams.ui.common.Constantes
import com.moviles.appf1teams.utils.StringProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AddTeam(),
            Delete(),
            Update(),
            GetTeams(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)

            viewModel.loadTeam()

            viewModel.uiState.observe(this@MainActivity) { state ->
                state.error?.let { error ->
                    Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                }
                if (state.error == null) {
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
                    viewModel.addTeam(
                        textName.text.toString(),
                        slider.value,
                        num,
                        switchMaterial.isChecked
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
                        viewModel.previousTeam()
                        true
                    }
                    R.id.itemRight -> {
                        viewModel.nextTeam()
                        true
                    }
                    R.id.itemUpdate -> {
                        if (textName.text.toString() != Constantes.EmptyText && toggleButton.checkedButtonId != Constantes.NotFound) {
                            val num = getToggleButton()
                            viewModel.updateTeam(
                                textName.text.toString(),
                                slider.value,
                                num,
                                switchMaterial.isChecked
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
                        viewModel.deleteTeam()
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
