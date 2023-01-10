package com.moviles.f1app.ui.pantalla.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.moviles.f1app.R
import com.moviles.f1app.databinding.ActivityUserBinding
import com.moviles.f1app.ui.pantalla.admin.AdminActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding

    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            setSupportActionBar(topAppBar)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentUser) as NavHostFragment
            navController = navHostFragment.findNavController()
            setupActionBarWithNavController(navController, AppBarConfiguration(navController.graph))

            navView.setupWithNavController(navController)

            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.itemIrAdmin, R.id.itemIrUser
                ), drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

            topAppBar.setNavigationOnClickListener {
                Log.i("TAG", navController.currentDestination?.id.toString())
                drawerLayout.open()
            }

            topAppBar.navigationIcon = getDrawable(R.drawable.ic_menu_24)

            navView.setNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.itemIrAdmin -> {
                        val intent = Intent(this@UserActivity, AdminActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.itemIrUser -> {
                        val intent = Intent(this@UserActivity, UserActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.item_drivers -> {
                        navController.navigate(R.id.watch_drivers)
                        true
                    }
                    R.id.item_teams -> {
                        navController.navigate(R.id.watch_teams)
                        true
                    }
                    R.id.item_races -> {
                        navController.navigate(R.id.watch_races)
                        true
                    }
                    else -> false
                }
            }


            navController.addOnDestinationChangedListener { _, destination, _ ->
                topAppBar.isVisible = true
                topAppBar.navigationIcon = getDrawable(R.drawable.ic_menu_24)

                when (destination.id) {
                    R.id.watch_drivers -> {
                        topAppBar.title = "Drivers"
                    }
                    R.id.watch_teams -> {
                        topAppBar.title = "Teams"
                    }
                    R.id.watch_races -> {
                        topAppBar.title = "Races"
                    }
                }
            }
        }
    }
}