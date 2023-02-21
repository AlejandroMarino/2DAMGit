package com.example.examenmoviles.ui.screens.hospitales

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examenmoviles.domain.modelo.Hospital
import com.example.examenmoviles.domain.modelo.Paciente

@Composable
fun HospitalesScreen(
    bottomBar: @Composable () -> Unit,
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
                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(16.dp),
                    Arrangement.SpaceEvenly
                ) {
                    ListHospitales(
                        Modifier,
                        hospitales = viewModel.uiState.collectAsState().value.hospitales
                    ) { hospital -> viewModel.handleEvent(HospitalesEvent.GetPacientes(hospital)) }
                    ListPacientes(
                        Modifier,
                        pacientes = viewModel.uiState.collectAsState().value.pacientes
                    )
                }
            },
            bottomBar = bottomBar
        )
    }
}


@Composable
fun ListHospitales(
    modifier: Modifier,
    hospitales: List<Hospital>,
    getPacientes: (Hospital) -> Unit
) {
    LazyColumn {
        items(
            items = hospitales,
            itemContent = {
                ItemsHospital(it, modifier, getPacientes)
            },
        )
    }
}

@Composable
fun ListPacientes(
    modifier: Modifier,
    pacientes: List<Paciente>,
) {
    LazyColumn {
        items(
            items = pacientes,
            itemContent = {
                ItemsPacientes(it, modifier)
            },
        )
    }
}

@Composable
fun ItemsHospital(hospital: Hospital, modifier: Modifier, getPacientes: (Hospital) -> Unit) {
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
fun ItemsPacientes(paciente: Paciente, modifier: Modifier) {
    Card(
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        elevation = 7.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)

    ) {
        Text(
            text = paciente.nombre,
            modifier = modifier.padding(16.dp)
        )
    }
}

