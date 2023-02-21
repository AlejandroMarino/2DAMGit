package com.example.examenmoviles.ui.screens.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.examenmoviles.ui.screens.hospitales.HospitalesScreen
import com.example.examenmoviles.ui.screens.pacientes.PacientesScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "hospitales") {
        composable("hospitales") {
            HospitalesScreen(
                { BottomBar(navController = navController) }
            )
        }
        composable("pacientes") {
            PacientesScreen(
                { BottomBar(navController = navController) }
            )
        }
    }
}