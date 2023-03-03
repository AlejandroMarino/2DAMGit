package com.example.examenmoviles.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.examenmoviles.R
import com.example.examenmoviles.databinding.ActivityMainXmlactivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainXMLActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainXmlactivityBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainXmlactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            setSupportActionBar(topAppBar)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
            navController = navHostFragment.findNavController()
            setupActionBarWithNavController(navController, AppBarConfiguration(navController.graph))

            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.item_hospitales -> {
                        navController.navigate(R.id.hospitales)
                        true
                    }
                    R.id.item_pacientes -> {
                        navController.navigate(R.id.pacientes)
                        true
                    }
                    else -> false
                }
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.hospitales -> {
                        topAppBar.isVisible = false
                    }
                    R.id.pacientes -> {
                        topAppBar.isVisible = true
                        topAppBar.title = "Pacientes"
                    }
                    R.id.detallePacientes -> {
                        topAppBar.isVisible = true
                        topAppBar.title = "Paciente"
                        topAppBar.setNavigationOnClickListener{
                            navController.navigateUp()
                        }
                    }
                }
            }
        }
    }
}
