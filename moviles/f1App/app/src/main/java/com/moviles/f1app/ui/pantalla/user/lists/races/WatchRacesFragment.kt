package com.moviles.f1app.ui.pantalla.user.lists.races

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moviles.f1app.databinding.FragmentWatchRacesBinding
import com.moviles.f1app.domain.modelo.Race
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchRacesFragment : Fragment() {

    private lateinit var adapter: WatchRacesAdapter

    private var _binding: FragmentWatchRacesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WatchRacesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWatchRacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.handleEvent(WatchRacesEvent.LoadRaces)

        with(binding) {

            adapter = WatchRacesAdapter(object : WatchRacesAdapter.RacesActions {
                override fun onRaceWatch(race: Race) {
                    val action = WatchRacesFragmentDirections.actionWatchRacesToWatchRace(race.id)
                    findNavController().navigate(action)
                }
            })
            list.adapter = adapter

            viewModel.uiState.observe(viewLifecycleOwner) {
                adapter.submitList(it.races)
            }
        }
    }
}