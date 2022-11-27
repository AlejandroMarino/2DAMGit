package com.moviles.appf1teams.ui.pantalla.drivers

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moviles.appf1teams.R
import com.moviles.appf1teams.databinding.ActivityDriversBinding
import com.moviles.appf1teams.domain.modelo.Driver
import com.moviles.appf1teams.ui.common.Constantes
import com.moviles.appf1teams.utils.StringProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriversActivity : AppCompatActivity() {
    private lateinit var adapter: DriversAdapter
    private lateinit var binding: ActivityDriversBinding
    private val viewModel: DriversViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idTeam = intent.getIntExtra(Constantes.id, Constantes.NotFound)

        viewModel.handleEvent(DriversEvent.LoadDrivers(idTeam))

        binding = ActivityDriversBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(binding.root)
            adapter = DriversAdapter(object : DriversAdapter.DriverActions {
                override fun onClickDelete(driver: Driver) {
                    viewModel.handleEvent(DriversEvent.DeleteDriver(idTeam, driver))
                    Toast.makeText(this@DriversActivity, R.string.driver_deleted, Toast.LENGTH_SHORT)
                        .show()
                }
            })
            drivers.adapter = adapter

            viewModel.uiState.observe(this@DriversActivity) { state ->
                state.message?.let { error ->
                    Toast.makeText(this@DriversActivity, error, Toast.LENGTH_SHORT).show()
                }
            }

            viewModel.uiState.observe(this@DriversActivity) { state ->
                state.message?.let { error ->
                    Toast.makeText(this@DriversActivity, error, Toast.LENGTH_SHORT).show()
                }
                adapter.submitList(state.drivers)
            }

            buttonAdd.setOnClickListener {
                if (textName.text.toString().isBlank() || number.text.toString().isBlank()) {
                    Toast.makeText(
                        this@DriversActivity,
                        R.string.attrRequired,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.handleEvent(
                        DriversEvent.AddDriver(
                            idTeam,
                            Driver(
                                name = textName.text.toString(),
                                number = number.text.toString().toInt()
                            )
                        )
                    )
                }
            }
        }

    }
}