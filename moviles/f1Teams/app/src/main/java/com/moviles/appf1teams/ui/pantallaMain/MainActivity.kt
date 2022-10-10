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

    private var actualIndex: Int = Constantes.FirstOfList


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

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)

            viewModel.cargarTeam(actualIndex)

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
                    }
                    switchMaterial.isChecked = state.team.winner
                }
            }

            floatingActionButton.setOnClickListener {
                if (textName.text.toString() != Constantes.EmptyText && toggleButton.checkedButtonId != Constantes.NotFound) {
                    var num = Constantes.Zero
                    when (toggleButton.checkedButtonId) {
                        R.id.buttonS -> {
                            num = Constantes.IntSoft
                        }
                        R.id.buttonM -> {
                            num = Constantes.IntMedium
                        }
                        R.id.buttonH -> {
                            num = Constantes.IntHard
                        }
                    }
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

            bottomNavigationView.setOnNavigationItemReselectedListener { item ->
                when (item.itemId) {
                    R.id.itemLeft -> {
                        val newIndex = viewModel.previousTeam(actualIndex)
                        actualIndex = newIndex

                    }
                    R.id.itemRight -> {
                        val newIndex = viewModel.nextTeam(actualIndex)
                        actualIndex = newIndex

                    }
                    R.id.itemUpdate -> {
                        if (textName.text.toString() != Constantes.EmptyText && toggleButton.checkedButtonId != Constantes.NotFound) {
                        var num = Constantes.Zero
                        when (toggleButton.checkedButtonId) {
                            R.id.buttonS -> {
                                num = Constantes.IntSoft
                            }
                            R.id.buttonM -> {
                                num = Constantes.IntMedium
                            }
                            R.id.buttonH -> {
                                num = Constantes.IntHard
                            }
                        }
                        viewModel.updateTeam(
                            actualIndex,
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
                    R.id.itemDelete -> {
                        val n = viewModel.deleteTeam(actualIndex)
                        actualIndex = n
                    }
                }
            }

        }
    }


}
