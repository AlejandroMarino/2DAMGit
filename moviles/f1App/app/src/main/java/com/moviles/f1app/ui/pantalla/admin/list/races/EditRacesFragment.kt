package com.moviles.f1app.ui.pantalla.admin.list.races

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.moviles.f1app.R
import com.moviles.f1app.databinding.FragmentEditRacesBinding
import com.moviles.f1app.domain.modelo.Race
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditRacesFragment : Fragment() {

    private lateinit var adapter: RacesAdapter

    private lateinit var binding: FragmentEditRacesBinding

    private val viewModel: EditRacesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_races, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditRacesBinding.inflate(layoutInflater)
        with(binding) {
            adapter = RacesAdapter(object : RacesAdapter.RacesActions {
                override fun onRaceWatch(race: Race) {
                    val id: Int = race.id
                    val action = EditRacesFragmentDirections.actionEditRacesToEditRace(id)
                    findNavController().navigate(action)
                }

                override fun onRaceDelete(race: Race) {
                    viewModel.handleEvent(EditRacesEvent.DeleteRace(race))
                    Snackbar.make(binding.root, R.string.team_Deleted, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo) {
                            viewModel.handleEvent(EditRacesEvent.AddRace(race))
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
                adapter.submitList(state.races)
            }
        }
    }


}