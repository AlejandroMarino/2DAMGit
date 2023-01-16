package com.moviles.f1app.ui.pantalla.user.detail.race

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moviles.f1app.databinding.FragmentWatchRaceBinding
import com.moviles.f1app.domain.modelo.PerformanceWithObjects
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class WatchRaceFragment : Fragment() {
    private lateinit var adapter: WatchPerformanceAdapterRace

    private var _binding: FragmentWatchRaceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WatchRaceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWatchRaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val idRace = arguments?.getInt("idRace") ?: 0
        viewModel.handleEvent(WatchRaceEvent.GetRace(idRace))

        with(binding) {
            adapter = WatchPerformanceAdapterRace(object :
                WatchPerformanceAdapterRace.PerformanceActions {
                override fun onClickWatch(performance: PerformanceWithObjects) {
                    val action =
                        WatchRaceFragmentDirections.actionWatchRaceToWatchDriver(performance.driver.id)
                    findNavController().navigate(action)
                }
            })
            list.adapter = adapter

            viewModel.uiState.observe(viewLifecycleOwner) { state ->
                state.race.let {
                    textTrack.text = it.track
                    textDate.text = it.date.format(formatter)
                }
                adapter.submitList(state.performances)
            }
        }
    }
}