package com.example.composeflows.ui.screens.movies

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.composeflows.R
import com.example.composeflows.common.Constantes
import com.example.composeflows.domain.modelo.Movie

@Composable
fun MoviesScreen(
    navigateToMovieDetail: (Int) -> Unit,
    bottomBar: @Composable () -> Unit,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(MoviesEvent.GetPopularMovies)
    }
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            content = {
                List(
                    Modifier.padding(it),
                    viewModel.uiState.collectAsState().value.movies ?: emptyList(),
                    navigateToMovieDetail,
                    viewModel.uiState.collectAsState().value.isLoading
                )
                Error(viewModel.uiState.collectAsState().value.error, viewModel)
            },
            bottomBar = bottomBar
        )
    }
}

@Composable
fun Error(error: String?, viewModel: MoviesViewModel) {
    val context = LocalContext.current
    if (!error.isNullOrEmpty()) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        viewModel.handleEvent(MoviesEvent.ErrorCaught)
    }
}

@Composable
fun List(
    modifier: Modifier,
    movies: List<Movie>,
    navigateToMovieDetail: (Int) -> Unit,
    isLoading: Boolean
) {
    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        LazyRow(
            contentPadding = PaddingValues(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxSize()
        ) {
            items(items = movies, itemContent = {
                MovieItem(it, navigateToMovieDetail, modifier)
            })
        }
    }
}

@Composable
fun MovieItem(movie: Movie, navigateToMovieDetail: (Int) -> Unit, modifier: Modifier) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .width(300.dp)
            .clickable { navigateToMovieDetail(movie.id) },
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
    ) {
        AsyncImage(
            model = Constantes.imageUrl + movie.posterPath,
            contentDescription = stringResource(id = R.string.movie_poster),
            modifier = Modifier
                .fillMaxWidth()
        )
    }

}