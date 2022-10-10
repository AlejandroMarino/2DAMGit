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
import com.moviles.appf1teams.utils.StringProvider

class MainActivity : AppCompatActivity() {

    private var actualIndex: Int = 0


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
                    viewModel.errorMostrado()
                }
                if (state.error == null) {
                    textName.setText(state.team.name)
                    slider.value = state.team.performance
                    when (state.team.tyre) {
                        1 -> {
                            toggleButton.check(R.id.buttonS)
                        }
                        2 -> {
                            toggleButton.check(R.id.buttonM)
                        }
                        3 -> {
                            toggleButton.check(R.id.buttonH)
                        }
                    }
                    switchMaterial.isChecked = state.team.winner
                }
            }

            floatingActionButton.setOnClickListener {
                var num = 0
                when (toggleButton.checkedButtonId) {
                    R.id.buttonS -> {
                        num = 1
                    }
                    R.id.buttonM -> {
                        num = 2
                    }
                    R.id.buttonH -> {
                        num = 3
                    }
                }
                viewModel.addTeam(
                    textName.text.toString(),
                    slider.value,
                    num,
                    switchMaterial.isChecked
                )
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
                        var num = 0
                        when (toggleButton.checkedButtonId) {
                            R.id.buttonS -> {
                                num = 1
                            }
                            R.id.buttonM -> {
                                num = 2
                            }
                            R.id.buttonH -> {
                                num = 3
                            }
                        }
                        viewModel.updateTeam(
                            actualIndex,
                            textName.text.toString(),
                            slider.value,
                            num,
                            switchMaterial.isChecked
                        )
                    }
                    R.id.itemDelete -> {
                        viewModel.deleteTeam(viewModel.getNameTeam(actualIndex))

                    }
                }
            }

        }
    }


}
