package com.example.examenalejandromarino.ui.pantallas.addEquipos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.examenalejandromarino.databinding.ActivityAddequipoBinding
import com.example.examenalejandromarino.domain.modelo.Equipo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEquiposActivity : AppCompatActivity() {

    private val viewModel: AddEquiposViewModel by viewModels()

    private lateinit var binding: ActivityAddequipoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddequipoBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)

            buttonAdd.setOnClickListener {
                viewModel.handleEvent(
                    AddEquiposEvent.AddEquipo(
                        Equipo(
                            nombre = textName.text.toString(),
                            nacionalidad = textNacionalidad.text.toString(),
                            puesto = number.text.toString().toInt()
                        )
                    )
                )
            }

            viewModel.uiState.observe(this@AddEquiposActivity) { state ->
                state.mensaje?.let { mensaje ->
                    Toast.makeText(this@AddEquiposActivity, mensaje, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}