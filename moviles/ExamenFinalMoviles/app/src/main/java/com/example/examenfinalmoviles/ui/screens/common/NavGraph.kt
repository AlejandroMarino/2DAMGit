package com.example.examenfinalmoviles.ui.screens.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.examenfinalmoviles.ui.screens.detalleDiputado.DetalleDiputadoScreen
import com.example.examenfinalmoviles.ui.screens.diputados.DiputadosScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "hospitales") {

        composable("diputados") {
            DiputadosScreen(
                { TopBar(goBack = { navController.popBackStack() }, title = "Diputados")},
                { id -> navController.navigate("detalleDiputado/$id") }
            )
        }
        composable(
            "detalleDiputado/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) {
            val id = it.arguments?.getString("id")
            requireNotNull(id)
            DetalleDiputadoScreen(
                id = id
            ) { TopBar(goBack = { navController.popBackStack() }, title = "Diputado") }
        }
    }
}