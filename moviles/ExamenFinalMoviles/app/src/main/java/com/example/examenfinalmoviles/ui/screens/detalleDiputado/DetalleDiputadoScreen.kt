package com.example.examenfinalmoviles.ui.screens.detalleDiputado

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examenfinalmoviles.domain.modelo.Diputado
import com.example.examenfinalmoviles.ui.screens.diputados.Error
import java.util.*

@Composable
fun DetalleDiputadoScreen(
    id: String,
    topBar: @Composable () -> Unit,
    viewModel: DetalleDiputadoViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(DetalleDiputadoEvent.GetDiputado(UUID.fromString(id)))
    }
    val state = viewModel.uiState.collectAsState().value
    Box(
        Modifier.fillMaxSize()
    ) {
        Scaffold(topBar = topBar, content = {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center)
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    Content(
                        diputado = state.diputado, modifier = Modifier.padding(it)
                    )
                }
            }
            Error(
                state.error
            ) { viewModel.handleEvent(DetalleDiputadoEvent.ErrorCatch) }
        })
    }
}

@Composable
fun Content(diputado: Diputado, modifier: Modifier) {
    Column(
        modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Id: ${diputado.id}")
        Text(text = "Nombre: ${diputado.nombre}")
        Text(text = "Corrupto: ${diputado.corrupto}")
        Text(text = "Id Partido: ${diputado.idPartido}")
        Text(text = "Fecha de entrada al congreso: ${diputado.fechaEntradaCongreso}")
        if (diputado.causasConfirmadas != null) {
            Text(text = "Causas confirmadas: ")
            ListaCausas(
                causas = diputado.causasConfirmadas,
                confirmada = true,
                modifier = Modifier.weight(2f)
            )
        }
        if (diputado.causasSupuestas != null) {
            Text(text = "Causas supuestas: ")
            ListaCausas(diputado.causasSupuestas, false, Modifier.weight(2f))
        }
    }
}

@Composable
fun ListaCausas(causas: List<String>, confirmada: Boolean, modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = causas, itemContent = { causa ->
            ItemCausas(causa, confirmada, Modifier)
        })
    }
}

@Composable
fun ItemCausas(causa: String, confirmada: Boolean, modifier: Modifier) {
    Card(
        backgroundColor = if (confirmada) Color.Green else Color.Red,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(text = causa, modifier = Modifier.padding(16.dp))
    }
}