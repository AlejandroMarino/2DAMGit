package com.moviles.f1app.ui.pantalla.admin.detail.performance

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.moviles.f1app.R
import com.moviles.f1app.databinding.FragmentEditPerformanceBinding
import com.moviles.f1app.domain.modelo.Performance
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditPerformanceFragment : Fragment(), MenuProvider, AdapterView.OnItemClickListener {

    private var _binding: FragmentEditPerformanceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditPerformanceViewModel by viewModels()

    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditPerformanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)


        with(binding) {

            val idR = arguments?.getInt("idRace") ?: 0
            val idD = arguments?.getInt("idDriver") ?: 0

            viewModel.handleEvent(EditPerformanceEvent.GetData(idD, idR))

            var itemsD: Array<String>
            var itemsR: Array<String>

            viewModel.uiState.observe(viewLifecycleOwner) { state ->
                state.message?.let { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
                state.drivers.let { drivers ->
                    itemsD = drivers.map { it.name }.toTypedArray()
                    arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, itemsD)
                    textDriver.setAdapter(arrayAdapter)
                }
                state.races.let { races ->
                    itemsR = races.map { it.track }.toTypedArray()
                    arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, itemsR)
                    textRace.setAdapter(arrayAdapter)
                }
                state.performance.let { performance ->
                    if (performance.position != 0) {
                        textPos.setText(performance.position.toString())
                    }else{
                        textPos.setText("")
                    }
                    textFastestLap.setText(performance.fastestLap)
                }
                state.driverName.let {
                    textDriver.setText(it, false)
                }
                state.trackName.let {
                    textRace.setText(it, false)
                }
            }

            textDriver.onItemClickListener = this@EditPerformanceFragment
            textRace.onItemClickListener = this@EditPerformanceFragment
        }

    }

    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_lists, menu)
        menu.findItem(R.id.item_add).isVisible = true
        menu.findItem(R.id.item_update).isVisible = false

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            if (state.performance.driver.id != 0) {
                menu.findItem(R.id.item_add).isVisible = false
                menu.findItem(R.id.item_update).isVisible = true
            }else{
                menu.findItem(R.id.item_add).isVisible = true
                menu.findItem(R.id.item_update).isVisible = false
            }
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        with(binding) {
            return when (menuItem.itemId) {
                R.id.item_add -> {
                    if (textPos.text.toString() != "" && textFastestLap.text.toString() != "" &&
                        textDriver.text.toString() != "Driver" && textDriver.text.toString() != "" &&
                        textRace.text.toString() != "Race" && textRace.text.toString() != ""
                    ) {
                        viewModel.handleEvent(
                            EditPerformanceEvent.AddPerformance(
                                Performance(
                                    position = textPos.text.toString().toInt(),
                                    fastestLap = textFastestLap.text.toString()
                                ), textDriver.text.toString(), textRace.text.toString()
                            )
                        )
                    } else {
                        Toast.makeText(context, R.string.error_adding, Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                R.id.item_update -> {
                    if (textPos.text.toString() != "" && textFastestLap.text.toString() != "" &&
                        textDriver.text.toString() != "Driver" && textDriver.text.toString() != "" &&
                        textRace.text.toString() != "Race" && textRace.text.toString() != ""
                    ) {
                        viewModel.handleEvent(
                            EditPerformanceEvent.UpdatePerformance(
                                Performance(
                                    position = textPos.text.toString().toInt(),
                                    fastestLap = textFastestLap.text.toString()
                                ), textDriver.text.toString(), textRace.text.toString()
                            )
                        )
                    } else {
                        Toast.makeText(context, R.string.error_adding, Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        with(binding) {
            if (textRace.text.toString() != "" && textDriver.text.toString() != "") {
                viewModel.handleEvent(EditPerformanceEvent.GetPerformance(textDriver.text.toString(),textRace.text.toString()))
            }
        }

    }
}