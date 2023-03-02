package com.example.examenmoviles.ui.screens.pacientes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examenmoviles.ui.screens.common.TopBarFilter
import com.example.examenmoviles.ui.screens.detallePaciente.DetallePacienteEvent
import com.example.examenmoviles.ui.screens.detallePaciente.Error
import com.example.examenmoviles.ui.screens.hospitales.ListPacientes
import java.util.*

@Composable
fun PacientesScreen(
    bottomBar: @Composable () -> Unit,
    goDetallePaciente: (UUID) -> Unit,
    viewModel: PacientesViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(PacientesEvent.GetPacientes)
    }
    val state = viewModel.uiState.collectAsState().value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopBarFilter(
                    onTextChange = { text ->
                        viewModel.handleEvent(PacientesEvent.FilterPacientes(text))
                    }
                )
            },
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
                    ) {
                        ListPacientes(
                            Modifier,
                            pacientes = state.pacientes,
                            goDetallePaciente = goDetallePaciente
                        )
                    }
                }
                Error(
                    state.error
                ) { viewModel.handleEvent(PacientesEvent.ErrorCatch) }
            },
            bottomBar = bottomBar
        )
    }
}