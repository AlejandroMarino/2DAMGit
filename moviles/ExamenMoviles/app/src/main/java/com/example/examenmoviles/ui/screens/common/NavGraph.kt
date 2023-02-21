package com.example.examenmoviles.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.examenmoviles.ui.screens.hospitales.hospitalesScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "hospitales") {
        composable("hospitales") {
            hospitalesScreen(

            )
        }
    }
}