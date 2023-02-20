package com.example.composeflows.ui.screens.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.composeflows.R
import com.example.composeflows.ui.screens.movieDetail.MovieDetailScreen
import com.example.composeflows.ui.screens.movies.MoviesScreen
import com.example.composeflows.ui.screens.series.SeriesScreen
import com.example.composeflows.ui.screens.seriesDetail.SeriesDetailScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Constantes.movies) {
        composable(Constantes.movies){
            MoviesScreen(
                navigateToMovieDetail = { id ->
                    navController.navigate("movie_detail/$id")
                },
                { BottomBar(navController = navController) }
            )
        }
        composable(Constantes.series) {
            SeriesScreen(
                navigateToSeriesDetail = { id ->
                    navController.navigate("series_detail/$id")
                },
                { BottomBar(navController = navController) }
            )
        }
        composable(
            route = Constantes.movieDetail,
            arguments = listOf(
                navArgument(Constantes.id) { type = NavType.IntType }
            )
        ) {
            val id = it.arguments?.getInt(Constantes.id)
            requireNotNull(id)
            MovieDetailScreen(id = id, {
                TopBar(
                    goBack = { navController.popBackStack() },
                    title = stringResource(id = R.string.movie)
                )
            })
        }
        composable(
            route = Constantes.seriesDetail,
            arguments = listOf(
                navArgument(Constantes.id) { type = NavType.IntType }
            )
        ) {
            val id = it.arguments?.getInt(Constantes.id)
            requireNotNull(id)
            SeriesDetailScreen(id = id, {
                TopBar(
                    goBack = { navController.popBackStack() },
                    title = stringResource(id = R.string.series)
                )
            })
        }
    }
}

