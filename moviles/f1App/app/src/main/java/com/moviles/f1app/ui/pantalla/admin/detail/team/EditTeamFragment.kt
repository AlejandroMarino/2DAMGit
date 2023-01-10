package com.moviles.f1app.ui.pantalla.admin.detail.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.moviles.f1app.databinding.FragmentEditTeamBinding
import com.moviles.f1app.domain.modelo.Team
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTeamFragment : Fragment() {

    private var _binding: FragmentEditTeamBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditTeamViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            viewModel.uiState.observe(viewLifecycleOwner) { state ->
                state.message?.let { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }

            buttonAdd.setOnClickListener() {
                viewModel.handleEvent(
                    EditTeamEvent.AddTeam(
                        Team(
                            name = textName.text.toString(),
                            car = textCar.text.toString()
                        )
                    )
                )
            }


        }
    }


}