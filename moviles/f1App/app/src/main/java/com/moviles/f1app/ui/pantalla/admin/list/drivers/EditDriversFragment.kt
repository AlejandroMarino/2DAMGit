package com.moviles.f1app.ui.pantalla.admin.list.drivers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.moviles.f1app.R
import com.moviles.f1app.databinding.FragmentEditDriversBinding
import com.moviles.f1app.domain.modelo.Driver
import com.moviles.f1app.ui.pantalla.admin.list.teams.SwipeToDeleteTeam
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditDriversFragment : Fragment() {

    private lateinit var adapter: DriversAdapter

    private lateinit var binding: FragmentEditDriversBinding

    private val viewModel: EditDriversViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_drivers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditDriversBinding.inflate(layoutInflater)
        with(binding) {
            adapter = DriversAdapter(object : DriversAdapter.DriversActions {
                override fun onDriverWatch(driver: Driver) {
                    val id: Int = driver.id
                    val action = EditDriversFragmentDirections.actionEditDriversToEditDriver(id)
                    findNavController().navigate(action)
                }

                override fun onDriverDelete(driver: Driver) {
                    viewModel.handleEvent(EditDriversEvent.DeleteDriver(driver))
                    Snackbar.make(binding.root, R.string.team_Deleted, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo) {
                            viewModel.handleEvent(EditDriversEvent.AddDriver(driver))
                        }
                        .setBackgroundTint(resources.getColor(R.color.black))
                        .setTextColor(resources.getColor(R.color.white))
                        .setActionTextColor(resources.getColor(R.color.yellow_06))
                        .show()
                }
            })
            list.adapter = adapter

            val itemTouchHelper = ItemTouchHelper(SwipeToDeleteDriver(adapter))
            itemTouchHelper.attachToRecyclerView(list)

            viewModel.uiState.observe(viewLifecycleOwner) { state ->
                state.error?.let { error ->
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
                adapter.submitList(state.drivers)
            }

        }
    }
}