package com.moviles.f1app.ui.pantalla.user.lists.drivers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moviles.f1app.databinding.FragmentWatchDriversBinding
import com.moviles.f1app.domain.modelo.Driver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchDriversFragment : Fragment() {

    private lateinit var adapter: WatchDriversAdapter

    private var _binding: FragmentWatchDriversBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WatchDriversViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWatchDriversBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.handleEvent(WatchDriversEvent.LoadDrivers)

        with(binding) {

            adapter = WatchDriversAdapter(object : WatchDriversAdapter.DriversActions {
                override fun onDriverWatch(driver: Driver) {
                    val action =
                        WatchDriversFragmentDirections.actionWatchDriversToWatchDriver(driver.id)
                    findNavController().navigate(action)
                }
            })
            list.adapter = adapter

            viewModel.uiState.observe(viewLifecycleOwner) {
                adapter.submitList(it.drivers)
            }
        }
    }

}