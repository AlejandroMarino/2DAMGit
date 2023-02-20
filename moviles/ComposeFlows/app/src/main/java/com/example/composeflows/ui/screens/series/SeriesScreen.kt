package com.example.composeflows.ui.screens.series

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.composeflows.R
import com.example.composeflows.common.Constantes
import com.example.composeflows.domain.modelo.Series

@Composable
fun SeriesScreen(
    navigateToSeriesDetail: (Int) -> Unit,
    bottomBar: @Composable () -> Unit,
    viewModel: SeriesViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(SeriesEvent.GetPopularSeries)
    }
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            content = {
                List(
                    Modifier.padding(it),
                    viewModel.uiState.collectAsState().value.series ?: emptyList(),
                    navigateToSeriesDetail,
                    viewModel.uiState.collectAsState().value.isLoading
                )
            },
            bottomBar = bottomBar
        )
    }
}

@Composable
fun List(
    modifier: Modifier,
    series: List<Series>,
    navigateToSeriesDetail: (Int) -> Unit,
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
            items(items = series, itemContent = {
                SeriesItem(it, navigateToSeriesDetail, modifier)
            })
        }
    }
}

@Composable
fun SeriesItem(series: Series, navigateToSeriesDetail: (Int) -> Unit, modifier: Modifier) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .width(300.dp)
            .clickable { navigateToSeriesDetail(series.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Column {
            AsyncImage(
                model = Constantes.imageUrl + series.posterPath,
                contentDescription = stringResource(id = R.string.series_poster),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}