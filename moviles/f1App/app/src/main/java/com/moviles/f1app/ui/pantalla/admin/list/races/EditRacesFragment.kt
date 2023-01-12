package com.moviles.f1app.ui.pantalla.admin.list.races

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.moviles.f1app.R
import com.moviles.f1app.databinding.FragmentEditRacesBinding
import com.moviles.f1app.domain.modelo.Race
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditRacesFragment : Fragment(), MenuProvider {

    private lateinit var adapter: RacesAdapter

    private var _binding: FragmentEditRacesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditRacesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditRacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.handleEvent(EditRacesEvent.LoadRaces)

        with(binding) {
            adapter = RacesAdapter(object : RacesAdapter.RacesActions {
                override fun onRaceWatch(race: Race) {
                    val id: Int = race.id
                    val action = EditRacesFragmentDirections.actionEditRacesToEditRace(id)
                    findNavController().navigate(action)
                }

                override fun onRaceDelete(race: Race) {
                    viewModel.handleEvent(EditRacesEvent.DeleteRace(race))
                    Snackbar.make(binding.root, R.string.deleted, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo) {
                            viewModel.handleEvent(EditRacesEvent.AddRace(race))
                        }
                        .setBackgroundTint(resources.getColor(R.color.black))
                        .setTextColor(resources.getColor(R.color.white))
                        .setActionTextColor(resources.getColor(R.color.yellow_06))
                        .show()
                }
            })
            list.adapter = adapter

            val itemTouchHelper = ItemTouchHelper(SwipeToDeleteRace(adapter))
            itemTouchHelper.attachToRecyclerView(list)

            viewModel.uiState.observe(viewLifecycleOwner) { state ->
                state.error?.let { error ->
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
                adapter.submitList(state.races)
            }
        }
    }

    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_lists, menu)

        menu.findItem(R.id.item_add).isVisible = true
        menu.findItem(R.id.item_update).isVisible = false
        menu.findItem(R.id.item_delete).isVisible = false
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.item_add -> {
                val action = EditRacesFragmentDirections.actionEditRacesToEditRace(0)
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