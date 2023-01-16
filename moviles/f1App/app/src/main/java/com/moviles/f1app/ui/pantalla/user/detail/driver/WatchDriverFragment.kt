package com.moviles.f1app.ui.pantalla.user.detail.driver

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moviles.f1app.R
import com.moviles.f1app.databinding.FragmentWatchDriverBinding
import com.moviles.f1app.domain.modelo.PerformanceWithObjects
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchDriverFragment : Fragment() {

    private lateinit var adapter: WatchPerformanceAdapterDriver

    private var _binding: FragmentWatchDriverBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WatchDriverViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWatchDriverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idDriver = arguments?.getInt("idDriver") ?: 0
        viewModel.handleEvent(WatchDriverEvent.GetDriver(idDriver))

        with(binding) {
            adapter = WatchPerformanceAdapterDriver()
            listRaces.adapter = adapter

            viewModel.uiState.observe(viewLifecycleOwner) { state ->
                state.driver.let {
                    textName.text = it.name
                    textNumber.text = it.number.toString()
                    driverPhoto.setImageURI(Uri.parse(it.photo))
                    driverPhoto.imageTintList = null
                }
                state.teamName.let {
                    textTeam.text = it
                }
                adapter.submitList(state.performances)
            }
        }
    }

}