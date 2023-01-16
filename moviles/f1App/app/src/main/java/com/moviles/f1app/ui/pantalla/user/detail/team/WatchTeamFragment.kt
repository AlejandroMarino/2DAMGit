package com.moviles.f1app.ui.pantalla.user.detail.team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moviles.f1app.R
import com.moviles.f1app.databinding.FragmentWatchTeamBinding
import com.moviles.f1app.domain.modelo.Driver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchTeamFragment : Fragment() {
    private lateinit var adapter: DriversAdapter

    private var _binding: FragmentWatchTeamBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WatchTeamViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWatchTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idTeam = arguments?.getInt("idTeam") ?: 0

        viewModel.handleEvent(WatchTeamEvent.GetTeam(idTeam))

        with(binding) {

            adapter = DriversAdapter(object : DriversAdapter.DriversActions {
                override fun onClickWatch(driver: Driver) {
                    val action = WatchTeamFragmentDirections.actionWatchTeamToWatchDriver(driver.id)
                    findNavController().navigate(action)
                }
            })
            list.adapter = adapter

            viewModel.uiState.observe(viewLifecycleOwner) { state->
                state.team.let {
                    textName.text = it.name
                    textCar.text = it.car
                }
                adapter.submitList(state.drivers)
            }
        }
    }
}