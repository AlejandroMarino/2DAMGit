package com.moviles.f1app.ui.pantalla.admin.list.drivers

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
import com.moviles.f1app.databinding.FragmentEditDriversBinding
import com.moviles.f1app.domain.modelo.Driver
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditDriversFragment : Fragment(), MenuProvider {

    private lateinit var adapter: DriversAdapter

    private var _binding: FragmentEditDriversBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditDriversViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditDriversBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.handleEvent(EditDriversEvent.LoadDrivers)

        with(binding) {
            adapter = DriversAdapter(object : DriversAdapter.DriversActions {
                override fun onDriverWatch(driver: Driver) {
                    val id: Int = driver.id
                    val action = EditDriversFragmentDirections.actionEditDriversToEditDriver(id)
                    findNavController().navigate(action)
                }

                override fun onDriverDelete(driver: Driver) {
                    viewModel.handleEvent(EditDriversEvent.DeleteDriver(driver))
                    Snackbar.make(binding.root, R.string.deleted, Snackbar.LENGTH_LONG)
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

    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_lists, menu)

        menu.findItem(R.id.item_add).isVisible = true
        menu.findItem(R.id.item_update).isVisible = false
        menu.findItem(R.id.item_delete).isVisible = false
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.item_add -> {
                val action = EditDriversFragmentDirections.actionEditDriversToEditDriver(0)
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