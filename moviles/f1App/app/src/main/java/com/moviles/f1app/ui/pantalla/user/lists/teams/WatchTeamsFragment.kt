package com.moviles.f1app.ui.pantalla.user.lists.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.moviles.f1app.R
import com.moviles.f1app.databinding.FragmentWatchTeamsBinding
import com.moviles.f1app.domain.modelo.Team
import com.moviles.f1app.ui.pantalla.admin.list.teams.EditTeamsEvent
import com.moviles.f1app.ui.pantalla.admin.list.teams.EditTeamsFragmentDirections
import com.moviles.f1app.ui.pantalla.admin.list.teams.TeamsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchTeamsFragment : Fragment() {

    private lateinit var adapter: WatchTeamsAdapter

    private val viewModel: WatchTeamsViewModel by viewModels()

    private var _binding: FragmentWatchTeamsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWatchTeamsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.handleEvent(WatchTeamsEvent.LoadTeams)

        with(binding) {
            adapter = WatchTeamsAdapter(object : WatchTeamsAdapter.TeamsActions {
                override fun onTeamWatch(team: Team) {
                    val action = WatchTeamsFragmentDirections.actionWatchTeamsToWatchTeam(team.id)
                    findNavController().navigate(action)
                }
            })
            list.adapter = adapter

            viewModel.uiState.observe(viewLifecycleOwner) {
                adapter.submitList(it.teams)
            }
        }
    }

}