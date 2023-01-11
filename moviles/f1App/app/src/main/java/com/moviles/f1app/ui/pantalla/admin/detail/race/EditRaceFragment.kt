package com.moviles.f1app.ui.pantalla.admin.detail.race

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.moviles.f1app.R
import com.moviles.f1app.databinding.FragmentEditRaceBinding
import com.moviles.f1app.ui.pantalla.admin.detail.team.EditTeamEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditRaceFragment : Fragment(), MenuProvider {

    private var _binding: FragmentEditRaceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditRaceViewModel by viewModels()

    private var idRace: Int = 0

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
                viewModel.handleEvent(EditRaceEvent.GetRace(id))
            } else {
                idRace = 0
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
                    true
                }
                R.id.item_update -> {
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