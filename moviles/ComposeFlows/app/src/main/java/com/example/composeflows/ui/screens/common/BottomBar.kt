package com.example.composeflows.ui.screens.common

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composeflows.R
import com.example.composeflows.ui.screens.common.Constantes.movies
import com.example.composeflows.ui.screens.common.Constantes.series

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
            selected = currentRoute?.hierarchy?.any { it.route == movies } == true,
            onClick = {
                navController.navigate(movies) {
                    popUpTo(movies) { inclusive = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            label = { Text(text = stringResource(id = R.string.movies)) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = androidx.compose.material.MaterialTheme.colors.onSecondary,
                unselectedIconColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                indicatorColor = androidx.compose.material.MaterialTheme.colors.onPrimary
            ),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.movies_icon_24),
                    contentDescription = stringResource(id = R.string.movies_icon),
                    modifier = Modifier.fillMaxHeight(0.35F)
                )
            },
            modifier = Modifier.fillMaxSize()
        )
        NavigationBarItem(
            selected = currentRoute?.hierarchy?.any { it.route == series } == true,
            onClick = {
                navController.navigate(series) {
                    popUpTo(series) { inclusive = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            label = { Text(text = stringResource(id = R.string.series)) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = androidx.compose.material.MaterialTheme.colors.onSecondary,
                unselectedIconColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                indicatorColor = androidx.compose.material.MaterialTheme.colors.onPrimary
            ),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.series_icon_24),
                    contentDescription = stringResource(id = R.string.series_icon),
                    modifier = Modifier.fillMaxHeight(0.35F)
                )
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
