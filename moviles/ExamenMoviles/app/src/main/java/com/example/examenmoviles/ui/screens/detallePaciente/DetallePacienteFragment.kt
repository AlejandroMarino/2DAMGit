package com.example.examenmoviles.ui.screens.detallePaciente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.examenmoviles.databinding.FragmentDetallePacienteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetallePacienteFragment : Fragment() {

    private lateinit var enfermedadesAdapter: EnfermedadesAdapter

    private var _binding: FragmentDetallePacienteBinding ?= null
    private val binding get() = _binding!!

    private val viewModel: DetallePacienteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetallePacienteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idPaciente = arguments?.getString("idPaciente")
        if (idPaciente != null) {
            viewModel.handleEvent(DetallePacienteEvent.GetPaciente(idPaciente))
        }

        with(binding) {
            enfermedadesAdapter = EnfermedadesAdapter()
            enfermedadesList.adapter = enfermedadesAdapter

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.uiState.collect { value ->
                    if (value.error.isNotBlank()) {
                        Toast.makeText(
                            requireContext(),
                            viewModel.uiState.value.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    textDNI.text = value.paciente.dni
                    textName.setText(value.paciente.nombre)
                    binding.loading.visibility =
                        if (value.isLoading) View.VISIBLE else View.GONE
                    enfermedadesAdapter.submitList(value.paciente.enfermedades)

                }
            }
        }
    }

}