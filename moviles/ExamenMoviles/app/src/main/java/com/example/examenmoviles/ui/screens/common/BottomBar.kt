package com.example.examenmoviles.ui.screens.common

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.examenmoviles.R

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    NavigationBar(
        containerColor = androidx.compose.material.MaterialTheme.colors.primary
    ) {
        NavigationBarItem(
            selected = currentRoute?.hierarchy?.any { it.route == "hospitales" } == true,
            onClick = {
                navController.navigate("hospitales") {
                    popUpTo("hospitales") { inclusive = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            label = { Text(text = "Hospitales") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = androidx.compose.material.MaterialTheme.colors.onSecondary,
                unselectedIconColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                indicatorColor = androidx.compose.material.MaterialTheme.colors.onPrimary
            ),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hospital_24),
                    contentDescription = "Icono hospital",
                    modifier = Modifier.fillMaxHeight(0.35F)
                )
            },
            modifier = Modifier.fillMaxSize()
        )
        NavigationBarItem(
            selected = currentRoute?.hierarchy?.any { it.route == "pacientes" } == true,
            onClick = {
                navController.navigate("pacientes") {
                    popUpTo("pacientes") { inclusive = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            label = { Text(text = "Pacientes") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = androidx.compose.material.MaterialTheme.colors.onSecondary,
                unselectedIconColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                indicatorColor = androidx.compose.material.MaterialTheme.colors.onPrimary
            ),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person_24),
                    contentDescription = "Icono paciente",
                    modifier = Modifier.fillMaxHeight(0.35F)
                )
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
