package com.moviles.f1app.ui.pantalla.admin.detail.team

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.moviles.f1app.R
import com.moviles.f1app.databinding.FragmentEditTeamBinding
import com.moviles.f1app.domain.modelo.Team
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTeamFragment : Fragment(), MenuProvider {

    private var _binding: FragmentEditTeamBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditTeamViewModel by viewModels()

    private var idTeam: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        with(binding) {

            val id = arguments?.getInt("idTeam")
            if (id != null && id != 0) {
                idTeam = id
                viewModel.handleEvent(EditTeamEvent.GetTeam(id))
            } else {
                idTeam = 0
            }

            viewModel.uiState.observe(viewLifecycleOwner) { state ->
                state.message?.let { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
                state.team.let { team ->
                    textName.setText(team.name)
                    textCar.setText(team.car)
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
                    if (idTeam != 0 && textName.text.toString() != "" && textCar.text.toString() != "") {
                        viewModel.handleEvent(
                            EditTeamEvent.AddTeam(
                                Team(
                                    name = textName.text.toString(),
                                    car = textCar.text.toString()
                                )
                            )
                        )
                    } else {
                        Toast.makeText(context, R.string.error_adding, Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                R.id.item_update -> {
                    if (idTeam != 0 && textName.text.toString() != "" && textCar.text.toString() != "") {
                        viewModel.handleEvent(
                            EditTeamEvent.UpdateTeam(
                                Team(
                                    id = idTeam,
                                    name = textName.text.toString(),
                                    car = textCar.text.toString()
                                )
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