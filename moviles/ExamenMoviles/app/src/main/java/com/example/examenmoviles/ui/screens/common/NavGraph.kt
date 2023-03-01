package com.example.examenmoviles.ui.screens.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.examenmoviles.ui.screens.detallePaciente.DetallePacienteScreen
import com.example.examenmoviles.ui.screens.hospitales.HospitalesScreen
import com.example.examenmoviles.ui.screens.pacientes.PacientesScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "hospitales") {
        composable("hospitales") {
            HospitalesScreen(
                { BottomBar(navController = navController) },
                { id -> navController.navigate("detallePaciente/$id") }
            )
        }
        composable("pacientes") {
            PacientesScreen(
                { BottomBar(navController = navController) },
                { id -> navController.navigate("detallePaciente/$id") }
            )
        }
        composable(
            "detallePaciente/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) {
            val id = it.arguments?.getString("id")
            requireNotNull(id)
            DetallePacienteScreen(id = id,
                { TopBar(goBack = { navController.popBackStack() }, title = "Paciente") }
            )
        }
    }
}