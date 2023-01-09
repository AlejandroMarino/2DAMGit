package com.moviles.f1app.ui.pantalla.admin.list.teams

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.moviles.f1app.R
import com.moviles.f1app.databinding.FragmentEditTeamsBinding
import com.moviles.f1app.domain.modelo.Team
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTeamsFragment : Fragment(),MenuProvider {

    private lateinit var adapter: TeamsAdapter

    private var _binding: FragmentEditTeamsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditTeamsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding  = FragmentEditTeamsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            viewModel.handleEvent(EditTeamsEvent.LoadTeams)
            adapter = TeamsAdapter(object : TeamsAdapter.TeamsActions {
                override fun onTeamWatch(team: Team) {
                    val id: Int = team.id
                    val action = EditTeamsFragmentDirections.actionEditTeamsToEditTeam(id)
                    findNavController().navigate(action)
                }

                override fun onTeamDelete(team: Team) {
                    viewModel.handleEvent(EditTeamsEvent.DeleteTeam(team))
                    Snackbar.make(binding.root, R.string.team_Deleted, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo) {
                            viewModel.handleEvent(EditTeamsEvent.AddTeam(team))
                        }
                        .setBackgroundTint(resources.getColor(R.color.black))
                        .setTextColor(resources.getColor(R.color.white))
                        .setActionTextColor(resources.getColor(R.color.purple_700))
                        .show()
                }
            })
            list.adapter = adapter

            viewModel.uiState.observe(viewLifecycleOwner) { state ->
                state.error?.let { error ->
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
                adapter.submitList(state.teams)
            }

            button.setOnClickListener() {
                val action = EditTeamsFragmentDirections.actionEditTeamsToEditTeam(0)
                findNavController().navigate(action)
            }

            textView.text = "Edit Teams"

        }
    }

    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_lists, menu)

        menu.findItem(R.id.edit_teams).isVisible = false
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.item_add -> {
                val action = EditTeamsFragmentDirections.actionEditTeamsToEditTeam(0)
                findNavController().navigate(action)
                true
            }
            R.id.item_delete -> {
                true
            }
            else -> false
        }
    }


}