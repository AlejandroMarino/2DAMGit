package com.example.examenmoviles.ui.screens.hospitales

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.examenmoviles.databinding.FragmentHospitalesBinding
import com.example.examenmoviles.domain.modelo.Hospital
import com.example.examenmoviles.domain.modelo.Paciente
import com.example.examenmoviles.ui.MainActivity
import com.example.examenmoviles.ui.screens.common.PacientesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HospitalesFragment : Fragment() {

    private lateinit var hospitalesAdapter: HospitalesAdapter
    private lateinit var pacientesAdapter: PacientesAdapter

    private var _binding: FragmentHospitalesBinding ?= null
    private val binding get() = _binding!!

    private val viewModel: HospitalesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHospitalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.handleEvent(HospitalesEvent.GetHospitales)

        with(binding) {
            hospitalesAdapter = HospitalesAdapter(object : HospitalesAdapter.HospitalesActions {
                override fun onHospitalDelete(hospital: Hospital) {
                    viewModel.handleEvent(HospitalesEvent.DeleteHospital(hospital))
                }

                override fun onHospitalClick(hospital: Hospital) {
                    viewModel.handleEvent(HospitalesEvent.GetPacientes(hospital))
                }
            })
            hospitalesList.adapter = hospitalesAdapter

            pacientesAdapter = PacientesAdapter(object : PacientesAdapter.PacientesActions {
                override fun onPacienteClick(paciente: Paciente) {
                    val id: String = paciente.id.toString()
                    val action = HospitalesFragmentDirections.actionHospitalesToDetallePacientes(id)
                    findNavController().navigate(action)
                }
            })
            pacientesList.adapter = pacientesAdapter

            val itemTouchHelper = ItemTouchHelper(SwipeToDeleteHospital(hospitalesAdapter))
            itemTouchHelper.attachToRecyclerView(hospitalesList)

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect { value ->
                        if (value.error.isNotBlank()) {
                            Toast.makeText(
                                requireContext(),
                                viewModel.uiState.value.error,
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.handleEvent(HospitalesEvent.ErrorCatch)
                        }
                        binding.loading.visibility =
                            if (value.isLoading) View.VISIBLE else View.GONE
                        hospitalesAdapter.submitList(value.hospitales)
                        pacientesAdapter.submitList(value.pacientes)
                    }
                }
            }

            goCompose.setOnClickListener {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }

    }

}