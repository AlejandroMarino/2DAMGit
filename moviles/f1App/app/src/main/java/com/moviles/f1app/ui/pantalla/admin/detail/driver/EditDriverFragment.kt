package com.moviles.f1app.ui.pantalla.admin.detail.driver

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.moviles.f1app.R
import com.moviles.f1app.databinding.FragmentEditDriverBinding
import com.moviles.f1app.domain.modelo.Driver
import com.moviles.f1app.domain.modelo.PerformanceWithObjects
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditDriverFragment : Fragment(), MenuProvider {
    private lateinit var adapter: PerformanceAdapterDriver

    private var _binding: FragmentEditDriverBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditDriverViewModel by viewModels()

    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var idDriver: Int = 0

    private val requestGallery = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditDriverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.handleEvent(EditDriverEvent.GetTeams)

        with(binding) {
            addPerformance.isVisible = false

            val id = arguments?.getInt("idDriver")
            if (id != null && id != 0) {
                idDriver = id
                viewModel.handleEvent(EditDriverEvent.GetDriver(id))
            } else {
                idDriver = 0
                textTeam.setText("", false)
            }

            adapter =
                PerformanceAdapterDriver(object : PerformanceAdapterDriver.PerformanceActions {
                    override fun onClickWatch(performance: PerformanceWithObjects) {
                        val action =
                            EditDriverFragmentDirections.actionEditDriverToEditPerformanceFragment(
                                performance.race.id,
                                performance.driver.id,
                            )
                        findNavController().navigate(action)
                    }

                    override fun onClickDelete(performance: PerformanceWithObjects) {
                        viewModel.handleEvent(EditDriverEvent.DeletePerformance(performance))
                        Snackbar.make(binding.root, "Performance deleted", Snackbar.LENGTH_LONG)
                            .setAction("Undo") {
                                viewModel.handleEvent(EditDriverEvent.AddPerformance(performance))
                            }
                            .setBackgroundTint(resources.getColor(R.color.black))
                            .setTextColor(resources.getColor(R.color.white))
                            .setActionTextColor(resources.getColor(R.color.yellow_06))
                            .show()
                    }
                })
            listPerformances.adapter = adapter

            val itemTouchHelper = ItemTouchHelper(SwipeToDeletePerformanceD(adapter))
            itemTouchHelper.attachToRecyclerView(listPerformances)

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
                    addPerformance.isVisible = driver.id != 0
                    textNameD.setText(driver.name)
                    textTeam.setText(
                        viewModel.uiState.value?.teamName,
                        false
                    )
                    if (driver.number != 0) {
                        textNumber.setText(driver.number.toString())
                    }
                }
                state.photo.let { photo ->
                    if (photo != "") {
                        imageDriver.setImageURI(Uri.parse(photo))
                        imageDriver.imageTintList = null
                    }else{
                        imageDriver.setImageResource(R.drawable.ic_person_24)
                    }
                }
                adapter.submitList(state.performances)
            }

            addPerformance.setOnClickListener {
                val action = EditDriverFragmentDirections.actionEditDriverToEditPerformanceFragment(
                    0,
                    viewModel.uiState.value?.driver?.id ?: 0
                )
                findNavController().navigate(action)
            }



            imageDriver.setOnClickListener {
                if (requireContext().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, requestGallery)
                } else {
                    gallery()
                }
            }

            onRequestPermissionsResult(
                requestGallery,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                intArrayOf(PackageManager.PERMISSION_GRANTED)
            )
        }
    }

    //Abre la galeria
    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, requestGallery)
    }

    //Obtiene la imagen seleccionada
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == requestGallery) {
            binding.imageDriver.setImageURI(data?.data)
            viewModel.handleEvent(EditDriverEvent.SetPhoto(data?.data.toString()))
        }
    }

    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_lists, menu)
        menu.findItem(R.id.item_add).isVisible = true
        menu.findItem(R.id.item_update).isVisible = true
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
                                    number = textNumber.text.toString().toInt(),
                                    photo = viewModel.uiState.value?.photo ?: "",
                                ), textTeam.text.toString()
                            )
                        )
                    } else {
                        Toast.makeText(context, R.string.error_adding, Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                R.id.item_update -> {
                    val idDriver = viewModel.uiState.value?.driver?.id ?: 0
                    if (idDriver != 0 && textNameD.text.toString() != "" &&
                        textNumber.text.toString() != "" && textTeam.text.toString() != "Team" &&
                        textTeam.text.toString() != ""
                    ) {
                        viewModel.handleEvent(
                            EditDriverEvent.UpdateDriver(
                                Driver(
                                    id = idDriver,
                                    name = textNameD.text.toString(),
                                    number = textNumber.text.toString().toInt(),
                                    photo = viewModel.uiState.value?.photo ?: "",
                                ), textTeam.text.toString()
                            )
                        )
                    } else {
                        Toast.makeText(context, R.string.error_updating, Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                else -> false
            }
        }
    }

}