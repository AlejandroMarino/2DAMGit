package com.example.composeflows.ui.screens.movieDetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.composeflows.common.Constantes
import com.example.composeflows.data.modelo.ResponseMovie
import com.example.composeflows.R

@Composable
fun MovieDetailScreen(
    id: Int,
    topBar: @Composable () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(MovieDetailEvent.GetMovieDetail(id))
    }

    Box(
        Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = topBar,
            content = {
                ContentMovies(
                    viewModel.uiState.collectAsState().value.movie,
                    viewModel.uiState.collectAsState().value.isLoading,
                    Modifier.padding(it)
                )
            }
        )
    }
}

@Composable
fun ContentMovies(movie: ResponseMovie?, isLoading: Boolean, modifier: Modifier) {
    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    AsyncImage(
                        model = Constantes.imageUrl + movie?.poster_path,
                        contentDescription = stringResource(id = R.string.movie_poster),
                        modifier = Modifier
                            .width(150.dp)
                    )
                }
                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = stringResource(id = R.string.title), modifier = Modifier.padding(start = 10.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = movie?.title ?: "", modifier = Modifier.padding(start = 25.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = stringResource(id = R.string.vote_average), modifier = Modifier.padding(start = 10.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = movie?.vote_average.toString(),
                        modifier = Modifier.padding(start = 25.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = stringResource(id = R.string.release_date), modifier = Modifier.padding(start = 10.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = movie?.release_date ?: "",
                        modifier = Modifier.padding(start = 25.dp)
                    )
                }
            }
            Row {
                LazyColumn(Modifier.padding(top = 16.dp)) {
                    item {
                        Text(text = stringResource(id = R.string.overview), modifier = Modifier.padding(start = 10.dp))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = movie?.overview ?: "",
                            modifier = Modifier.padding(start = 25.dp)
                        )
                    }
                }
            }
        }
    }
}