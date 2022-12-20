package com.example.examenalejandromarino.ui.pantallas.equipos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.examenalejandromarino.R
import com.example.examenalejandromarino.databinding.ActivityMainBinding
import com.example.examenalejandromarino.domain.modelo.Equipo
import com.example.examenalejandromarino.ui.pantallas.componentes.ComponentesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: EquiposAdapter

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.handleEvent(MainEvent.LoadEquipos)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun add(){
        with(binding){
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)

            adapter = EquiposAdapter(object : EquiposAdapter.EquipoActions {
                override fun onClickDelete(equipo: Equipo) {
                    viewModel.handleEvent(MainEvent.DeleteEquipo(equipo.id))
                }

                override fun onClickWatch(equipo: Equipo) {
                    val intent = Intent(this@MainActivity, ComponentesActivity::class.java)
                    intent.putExtra("id", equipo.id)
                    startActivity(intent)
                }
            })
            equipos.adapter = adapter

            viewModel.uiState.observe(this@MainActivity) { state ->
                state.mensaje?.let { mensaje ->
                    Toast.makeText(this@MainActivity, mensaje, Toast.LENGTH_SHORT).show()
                }
                adapter.submitList(state.equipos)
            }
        }
    }
}