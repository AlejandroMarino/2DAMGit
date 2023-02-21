package com.example.composeflows.ui.screens.seriesDetail

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
import com.example.composeflows.R
import com.example.composeflows.common.Constantes
import com.example.composeflows.data.modelo.ResponseSeries

@Composable
fun SeriesDetailScreen(
    id: Int,
    topBar: @Composable () -> Unit,
    viewModel: SeriesDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(SeriesDetailEvent.GetSeriesDetail(id))
    }

    Box(
        Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = topBar,
            content = {
                ContentSeries(
                    viewModel.uiState.collectAsState().value.series,
                    viewModel.uiState.collectAsState().value.isLoading,
                    Modifier.padding(it)
                )
            }
        )
    }

}

@Composable
fun ContentSeries(series: ResponseSeries?, isLoading: Boolean, modifier: Modifier) {
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
                        model = Constantes.imageUrl + series?.poster_path,
                        contentDescription = stringResource(id = R.string.series_poster),
                        modifier = Modifier
                            .width(150.dp)
                    )
                }
                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = stringResource(id = R.string.name), modifier = Modifier.padding(start = 10.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = series?.name ?: "", modifier = Modifier.padding(start = 25.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = stringResource(id = R.string.vote_average), modifier = Modifier.padding(start = 10.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = series?.vote_average.toString(),
                        modifier = Modifier.padding(start = 25.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = stringResource(id = R.string.first_air_date), modifier = Modifier.padding(start = 10.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = series?.first_air_date ?: "",
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
                            text = series?.overview ?: "",
                            modifier = Modifier.padding(start = 25.dp)
                        )
                    }
                }
            }
        }
    }
}