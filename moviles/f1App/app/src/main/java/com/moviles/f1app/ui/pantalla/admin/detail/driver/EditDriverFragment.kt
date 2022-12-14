package com.moviles.f1app.ui.pantalla.admin.detail.driver

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.moviles.f1app.R
import com.moviles.f1app.databinding.FragmentEditDriverBinding
import com.moviles.f1app.domain.modelo.Driver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDriverFragment : Fragment(), MenuProvider {

    private var _binding: FragmentEditDriverBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditDriverViewModel by viewModels()

    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var idDriver: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditDriverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.handleEvent(EditDriverEvent.GetTeams)

        with(binding) {

            val id = arguments?.getInt("idDriver")
            if (id != null && id != 0) {
                idDriver = id
                viewModel.handleEvent(EditDriverEvent.GetDriver(id))
            } else {
                idDriver = 0
                textTeam.setText("", false)
            }

            var items: Array<String>
            viewModel.uiState.observe(viewLifecycleOwner) { state ->
                state.message?.let { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
                state.teams.let { teams ->
                    items = teams.map { it.name }.toTypedArray()
                    arrayAdapter = ArrayAdapter(
                        requireContext(),
                        R.layout.item_dropdown,
                        items
                    )
                    textTeam.setAdapter(arrayAdapter)
                }
                state.driver.let { driver ->
                    textNameD.setText(driver.name)
                    textTeam.setText(
                        viewModel.uiState.value?.teamName,
                        false
                    )
                    if (driver.number != 0) {
                        textNumber.setText(driver.number.toString())
                    }
                }
            }

        }
    }

    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_lists, menu)

        menu.findItem(R.id.item_add).isVisible = true
        menu.findItem(R.id.item_update).isVisible = true
        menu.findItem(R.id.item_delete).isVisible = false
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        with(binding) {
            return when (menuItem.itemId) {
                R.id.item_add -> {
                    if (textNameD.text.toString() != "" && textNumber.text.toString() != "" &&
                        textTeam.text.toString() != "Team" && textTeam.text.toString() != ""
                    ) {
                        viewModel.handleEvent(
                            EditDriverEvent.AddDriver(
                                Driver(
                                    name = textNameD.text.toString(),
                                    number = textNumber.text.toString().toInt()
                                ), textTeam.text.toString()
                            )
                        )
                    } else {
                        Toast.makeText(context, R.string.error_adding, Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                R.id.item_update -> {
                    if (idDriver != 0 && textNameD.text.toString() != "" &&
                        textNumber.text.toString() != "" && textTeam.text.toString() != "Team" &&
                        textTeam.text.toString() != ""
                    ) {
                        viewModel.handleEvent(
                            EditDriverEvent.UpdateDriver(
                                Driver(
                                    id = idDriver,
                                    name = textNameD.text.toString(),
                                    number = textNumber.text.toString().toInt()
                                ), textTeam.text.toString()
                            )
                        )
                    } else {
                        Toast.makeText(context, R.string.error_updating, Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                R.id.item_delete -> {
                    true
                }
                else -> false
            }
        }
    }

}