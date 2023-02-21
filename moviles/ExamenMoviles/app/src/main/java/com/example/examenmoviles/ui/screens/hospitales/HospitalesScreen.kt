package com.example.examenmoviles.ui.screens.hospitales

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
fun hospitalesScreen(
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
                    modifier = Modifier.padding(it)
                ) {
                    ListHospitales(
                        Modifier,
                        hospitales = viewModel.uiState.collectAsState().value.hospitales,
                        viewModel
                        )
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
    viewModel: HospitalesViewModel
) {
    LazyColumn() {
        items(items = hospitales, itemContent = {
            Items(it.nombre, modifier)
        },)
    }
}

@Composable
fun ListPacientes(
    modifier: Modifier,
    pacientes: List<Paciente>,
) {
    LazyColumn() {
        items(items = pacientes, itemContent = {
            Items(it.nombre, modifier)
        })
    }
}

@Composable
fun Items(name: String, modifier: Modifier) {
    Card(
        elevation = 7.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(text = name)
    }
}

