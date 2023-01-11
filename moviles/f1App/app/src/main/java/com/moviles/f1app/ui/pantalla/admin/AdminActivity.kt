package com.moviles.f1app.ui.pantalla.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.moviles.f1app.R
import com.moviles.f1app.databinding.ActivityAdminBinding
import com.moviles.f1app.ui.pantalla.user.UserActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            setSupportActionBar(topAppBar)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentAdmin) as NavHostFragment
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
                        val intent = Intent(this@AdminActivity, AdminActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.itemIrUser -> {
                        val intent = Intent(this@AdminActivity, UserActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.item_drivers -> {
                        navController.navigate(R.id.edit_drivers)
                        true
                    }
                    R.id.item_teams -> {
                        navController.navigate(R.id.edit_teams)
                        true
                    }
                    R.id.item_races -> {
                        navController.navigate(R.id.edit_races)
                        true
                    }
                    else -> false
                }
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->
                topAppBar.isVisible = true

                when (destination.id) {
                    R.id.edit_drivers -> {
                        topAppBar.title = "Drivers"
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_menu_24)
                        topAppBar.setNavigationOnClickListener {
                            Log.i("TAG", navController.currentDestination?.id.toString())
                            drawerLayout.open()
                        }
                    }
                    R.id.edit_teams -> {
                        topAppBar.title = "Teams"
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_menu_24)
                        topAppBar.setNavigationOnClickListener {
                            Log.i("TAG", navController.currentDestination?.id.toString())
                            drawerLayout.open()
                        }
                    }
                    R.id.edit_races -> {
                        topAppBar.title = "Races"
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_menu_24)
                        topAppBar.setNavigationOnClickListener {
                            Log.i("TAG", navController.currentDestination?.id.toString())
                            drawerLayout.open()
                        }
                    }
                    R.id.edit_driver -> {
                        topAppBar.title = "Driver"
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_arrow_back_24)
                        topAppBar.setNavigationOnClickListener{
                            navController.navigateUp()
                        }
                    }
                    R.id.edit_race -> {
                        topAppBar.title = "Race"
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_arrow_back_24)
                        topAppBar.setNavigationOnClickListener{
                            navController.navigateUp()
                        }
                    }
                    R.id.edit_team -> {
                        topAppBar.title = "Team"
                        topAppBar.navigationIcon = getDrawable(R.drawable.ic_arrow_back_24)
                        topAppBar.setNavigationOnClickListener{
                            navController.navigateUp()
                        }
                    }
                }
            }
        }

        screenSplash.setKeepOnScreenCondition { false }
    }
}