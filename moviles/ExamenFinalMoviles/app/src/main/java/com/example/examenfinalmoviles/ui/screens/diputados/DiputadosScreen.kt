package com.example.examenfinalmoviles.ui.screens.diputados

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examenfinalmoviles.domain.modelo.Diputado
import com.example.examenfinalmoviles.domain.modelo.Partido
import java.util.*

@Composable
fun DiputadosScreen(
    topBar: @Composable () -> Unit,
    goDetalleDiputado: (UUID) -> Unit,
    viewModel: DiputadosViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(DiputadosEvent.GetPartidos)
    }
    val state = viewModel.uiState.collectAsState().value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            content = {
                if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.Center)
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        ListPartidos(
                            Modifier.weight(2f),
                            partidos = state.partidos,
                            getDiputados = { partido ->
                                viewModel.handleEvent(
                                    DiputadosEvent.GetDiputados(
                                        partido
                                    )
                                )
                            },
                        )
                        ListDiputados(
                            Modifier.weight(2f), diputados = state.diputados, goDetalleDiputado
                        )
                    }
                }
                Error(
                    state.error
                ) { viewModel.handleEvent(DiputadosEvent.ErrorCatch) }
            }, topBar = topBar
        )
    }
}

@Composable
fun Error(error: String, errorCaught: () -> Unit) {
    val context = LocalContext.current
    if (error.isNotEmpty()) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        errorCaught()
    }
}

@Composable
fun ListPartidos(
    modifier: Modifier,
    partidos: List<Partido>,
    getDiputados: (Partido) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(items = partidos, itemContent = { partido ->
            ItemsPartido(partido, modifier, getDiputados)
        })
    }
}

@Composable
fun ListDiputados(
    modifier: Modifier, diputados: List<Diputado>, goDetalleDiputado: (UUID) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            items = diputados,
            itemContent = { diputado ->
                ItemsDiputado(diputado, modifier, goDetalleDiputado)
            },
        )
    }
}

@Composable
fun ItemsPartido(
    partido: Partido,
    modifier: Modifier,
    getDiputados: (Partido) -> Unit,
) {
    Card(backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 7.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable { getDiputados(partido) }) {
        Text(
            text = partido.nombre,
            color = MaterialTheme.colors.onPrimary,
            modifier = modifier.padding(16.dp)
        )
    }
}

@Composable
fun ItemsDiputado(diputado: Diputado, modifier: Modifier, goDetalleDiputado: (UUID) -> Unit) {
    Card(backgroundColor = if (diputado.corrupto) Color.Red else MaterialTheme.colors.secondaryVariant,
        elevation = 7.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable { goDetalleDiputado(diputado.id) }) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = diputado.nombre, modifier = modifier.padding(16.dp)
            )
            Text(
                text = diputado.fechaEntradaCongreso.toString(), modifier = modifier.padding(16.dp)
            )
        }
    }
}