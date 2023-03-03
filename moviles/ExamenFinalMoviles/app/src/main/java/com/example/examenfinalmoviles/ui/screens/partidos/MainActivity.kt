package com.example.examenfinalmoviles.ui.screens.partidos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.examenfinalmoviles.databinding.ActivityMainBinding
import com.example.examenfinalmoviles.domain.modelo.Partido
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PartidosAdapter

    private lateinit var binding: ActivityMainBinding

    private val viewModel: PartidosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.handleEvent(PartidosEvent.GetPartidos)

        with(binding) {
            adapter = PartidosAdapter(object : PartidosAdapter.PartidosActions {
                override fun onPartidoClick(partido: Partido) {
                    viewModel.handleEvent(PartidosEvent.GetPartido(partido))
                }

                override fun onPartidoDelete(partido: Partido) {
                    viewModel.handleEvent(PartidosEvent.DeletePartido(partido))
                }
            })
            partidosList.adapter = adapter

            val itemTouchHelper = ItemTouchHelper(SwipeToDeletePartido(adapter))
            itemTouchHelper.attachToRecyclerView(partidosList)

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect { value ->
                        if (value.error.isNotBlank()) {
                            Toast.makeText(
                                this@MainActivity,
                                viewModel.uiState.value.error,
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.handleEvent(PartidosEvent.ErrorCatch)
                        }
                        if (value.partido.nombre != "") {
                            textID.text = value.partido.id.toString()
                            textNameD.setText(value.partido.nombre)
                        }
                        adapter.submitList(value.partidos)
                    }
                }
            }

            buttonAdd.setOnClickListener {
                viewModel.handleEvent(PartidosEvent.AddPartido(textNameD.text.toString()))
            }

            buttonUpdate.setOnClickListener {
                viewModel.handleEvent(PartidosEvent.UpdatePartido(textNameD.text.toString()))
            }
        }
    }
}