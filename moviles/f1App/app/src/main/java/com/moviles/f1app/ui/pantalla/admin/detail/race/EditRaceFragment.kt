package com.moviles.f1app.ui.pantalla.admin.detail.race

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.moviles.f1app.R
import com.moviles.f1app.databinding.FragmentEditRaceBinding
import com.moviles.f1app.domain.modelo.Performance
import com.moviles.f1app.domain.modelo.Race
import com.moviles.f1app.ui.pantalla.admin.detail.PerformanceAdapter
import com.moviles.f1app.ui.pantalla.admin.detail.SwipeToDeletePerformance
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class EditRaceFragment : Fragment(), MenuProvider {
    private lateinit var adapter: PerformanceAdapter

    private var _binding: FragmentEditRaceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditRaceViewModel by viewModels()

    private var idRace: Int = 0

    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditRaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        with(binding) {
            val id = arguments?.getInt("idRace")
            if (id != null && id != 0) {
                idRace = id
                viewModel.handleEvent(EditRaceEvent.GetData(id))
            } else {
                idRace = 0
            }

            adapter = PerformanceAdapter(object : PerformanceAdapter.PerformanceActions {
                override fun onClickWatch(performance: Performance) {
                    val action = EditRaceFragmentDirections.actionEditRaceToEditPerformanceFragment(
                        performance.idRace,
                        performance.idDriver
                    )
                    findNavController().navigate(action)
                }

                override fun onClickDelete(performance: Performance) {
                    viewModel.handleEvent(EditRaceEvent.DeletePerformance(performance))
                    Snackbar.make(binding.root, R.string.deleted, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo) {
                            viewModel.handleEvent(EditRaceEvent.AddPerformance(performance))
                        }
                        .setBackgroundTint(resources.getColor(R.color.black))
                        .setTextColor(resources.getColor(R.color.white))
                        .setActionTextColor(resources.getColor(R.color.yellow_06))
                        .show()
                }
            })
            listPerformances.adapter = adapter

            val itemTouchHelper = ItemTouchHelper(SwipeToDeletePerformance(adapter))
            itemTouchHelper.attachToRecyclerView(listPerformances)

            viewModel.uiState.observe(viewLifecycleOwner) { state ->
                state.message?.let { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
                state.race.let { race ->
                    textTrack.setText(race.track)
                    datePicker.text = race.date.format(formatter)
                }
                adapter.submitList(state.race.performances.toList().map { it.second })
            }


            addPerformance.setOnClickListener {
                val action =
                    EditRaceFragmentDirections.actionEditRaceToEditPerformanceFragment(idRace, 0)
                findNavController().navigate(action)
            }

            datePicker.setOnClickListener {
                val dialogFecha =
                    DatePikerFragment { day, month, year -> showDate(year, month, day) }
                dialogFecha.show(parentFragmentManager, "datePicker")
            }
        }
    }

    private fun showDate(day: Int, month: Int, year: Int) {
        binding.datePicker.text = "$day/${month}/$year"
    }

    class DatePikerFragment(
        val listener: (
            day: Int,
            month: Int,
            year: Int,
        ) -> Unit
    ) : DialogFragment(), DatePickerDialog.OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): DatePickerDialog {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            return DatePickerDialog(requireContext(), this, year, month, day)
        }

        override fun onDateSet(view: DatePicker?, day: Int, month: Int, year: Int) {
            listener(day, month + 1, year)
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
                    if (textTrack.text.toString() != "" ) {
                        viewModel.handleEvent(
                            EditRaceEvent.AddRace(
                                Race(
                                    track = textTrack.text.toString(),
                                    date = LocalDate.parse(datePicker.text.toString(), formatter),
                                )
                            )
                        )
                    } else {
                        Toast.makeText(context, R.string.error_adding, Toast.LENGTH_SHORT)
                            .show()
                    }
                    true
                }
                R.id.item_update -> {
                    if (idRace != 0 && textTrack.text.toString() != "") {
                        viewModel.handleEvent(
                            EditRaceEvent.UpdateRace(
                                Race(
                                    track = textTrack.text.toString(),
                                    date = LocalDate.parse(datePicker.text.toString(), formatter),
                                )
                            )
                        )
                    } else {
                        Toast.makeText(context, R.string.error_updating, Toast.LENGTH_SHORT)
                            .show()
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