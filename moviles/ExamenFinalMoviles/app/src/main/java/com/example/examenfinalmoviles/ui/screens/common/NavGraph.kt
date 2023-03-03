package com.example.examenfinalmoviles.ui.screens.common

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.examenfinalmoviles.ui.screens.detalleDiputado.DetalleDiputadoScreen
import com.example.examenfinalmoviles.ui.screens.diputados.DiputadosScreen
import com.example.examenfinalmoviles.ui.screens.partidos.MainActivity

@Composable
fun NavGraph(
    navController: NavHostController
) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = "diputados") {
        composable("diputados") {
            DiputadosScreen(
                {
                    TopBar(goBack = {
                        context.startActivity(
                            Intent(
                                context,
                                MainActivity::class.java
                            )
                        )
                    }, title = "Diputados")
                },
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
                id = id,
                { TopBar(goBack = { navController.popBackStack() }, title = "Diputado") }
            )
        }
    }
}