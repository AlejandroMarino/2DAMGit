package com.example.examenmoviles.ui.screens.pacientes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.examenmoviles.databinding.FragmentPacientesBinding
import com.example.examenmoviles.domain.modelo.Paciente
import com.example.examenmoviles.ui.screens.hospitales.HospitalesFragmentDirections
import com.example.examenmoviles.ui.screens.common.PacientesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PacientesFragment : Fragment() {

    private lateinit var pacientesAdapter: PacientesAdapter

    private var _binding: FragmentPacientesBinding ?= null
    private val binding get() = _binding!!

    private val viewModel: PacientesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPacientesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.handleEvent(PacientesEvent.GetPacientes)

        with(binding) {
            pacientesAdapter = PacientesAdapter(object : PacientesAdapter.PacientesActions {
                override fun onPacienteClick(paciente: Paciente) {
                    val id: String = paciente.id.toString()
                    val action = PacientesFragmentDirections.actionPacientesToDetallePacientes(id)
                    findNavController().navigate(action)
                }
            })
            pacientesList.adapter = pacientesAdapter

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.uiState.collect { value ->
                    if (value.error.isNotBlank()) {
                        Toast.makeText(
                            requireContext(),
                            viewModel.uiState.value.error,
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.handleEvent(PacientesEvent.ErrorCatch)
                    }
                    binding.loading.visibility =
                        if (value.isLoading) View.VISIBLE else View.GONE
                    pacientesAdapter.submitList(value.pacientes)
                }
            }
        }
    }
}