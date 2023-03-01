package com.example.examenmoviles.ui.screens.hospitales

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examenmoviles.domain.modelo.Hospital
import com.example.examenmoviles.domain.modelo.Paciente
import java.util.*

@Composable
fun HospitalesScreen(
    bottomBar: @Composable () -> Unit,
    goDetallePaciente: (UUID) -> Unit,
    viewModel: HospitalesViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(HospitalesEvent.GetHospitales)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            content = {
                if (viewModel.uiState.collectAsState().value.isLoading) {
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
                            .padding(16.dp),
                        Arrangement.SpaceEvenly
                    ) {
                        ListHospitales(
                            Modifier,
                            hospitales = viewModel.uiState.collectAsState().value.hospitales,
                            getPacientes = { hospital ->
                                viewModel.handleEvent(
                                    HospitalesEvent.GetPacientes(
                                        hospital
                                    )
                                )
                            },
                            deleteHospital = { hospital ->
                                viewModel.handleEvent(
                                    HospitalesEvent.DeleteHospital(
                                        hospital
                                    )
                                )
                            }
                        )
                        ListPacientes(
                            Modifier,
                            pacientes = viewModel.uiState.collectAsState().value.pacientes,
                            goDetallePaciente
                        )
                    }
                }
            },
            bottomBar = bottomBar
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListHospitales(
    modifier: Modifier,
    hospitales: List<Hospital>,
    getPacientes: (Hospital) -> Unit,
    deleteHospital: (Hospital) -> Unit
) {
    LazyColumn {
        items(
            items = hospitales,
            itemContent = { hospital ->
                val state = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd) {
                            deleteHospital(hospital)
                        }
                        true
                    }
                )
                SwipeToDismiss(
                    state = state,
                    background = {
                        val color = when (state.dismissDirection) {
                            DismissDirection.StartToEnd -> MaterialTheme.colors.error
                            DismissDirection.EndToStart -> MaterialTheme.colors.surface
                            null -> MaterialTheme.colors.surface
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = color)
                                .padding(14.5.dp)

                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete",
                                tint = MaterialTheme.colors.onPrimary
                            )
                            modifier.align(Alignment.CenterEnd)
                        }
                    },
                    dismissContent = {
                        ItemsHospital(hospital, modifier, getPacientes)
                    },
                    directions = setOf(DismissDirection.StartToEnd)
                )
            }
        )
    }
}

@Composable
fun ListPacientes(
    modifier: Modifier,
    pacientes: List<Paciente>,
    goDetallePaciente: (UUID) -> Unit
) {
    LazyColumn {
        items(
            items = pacientes,
            itemContent = {
                ItemsPacientes(it, modifier, goDetallePaciente)
            },
        )
    }
}

@Composable
fun ItemsHospital(
    hospital: Hospital,
    modifier: Modifier,
    getPacientes: (Hospital) -> Unit,
) {
    Card(
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 7.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable { getPacientes(hospital) }
    ) {
        Text(
            text = hospital.nombre,
            color = MaterialTheme.colors.onPrimary,
            modifier = modifier.padding(16.dp)
        )
    }
}

@Composable
fun ItemsPacientes(paciente: Paciente, modifier: Modifier, goDetallePaciente: (UUID) -> Unit) {
    Card(
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        elevation = 7.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable { goDetallePaciente(paciente.id) }
    ) {
        Text(
            text = paciente.nombre,
            modifier = modifier.padding(16.dp)
        )
    }
}

