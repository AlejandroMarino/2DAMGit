package com.example.examenmoviles.ui.screens.detallePaciente

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examenmoviles.R
import com.example.examenmoviles.domain.modelo.Enfermedad
import com.example.examenmoviles.domain.modelo.Paciente

@Composable
fun DetallePacienteScreen(
    id: String,
    topBar: @Composable () -> Unit,
    viewModel: DetallePacienteViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(DetallePacienteEvent.GetPaciente(id))
    }
    Box(
        Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = topBar,
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
                    ContentPaciente(
                        viewModel.uiState.collectAsState().value.paciente,
                        viewModel.uiState.collectAsState().value.enfermedad,
                        Modifier.padding(it),
                        onClickUpdate = { paciente ->
                            viewModel.handleEvent(
                                DetallePacienteEvent.UpdatePaciente(
                                    paciente
                                )
                            )
                        },
                        onClickAdd = { enfermedad ->
                            viewModel.handleEvent(
                                DetallePacienteEvent.AddEnfermedad(
                                    enfermedad
                                )
                            )
                        },
                        onValuePacienteChange = { value ->
                            viewModel.handleEvent(
                                DetallePacienteEvent.OnValuePacienteChange(
                                    Paciente(
                                        id = viewModel.uiState.value.paciente.id,
                                        nombre = value,
                                        dni = viewModel.uiState.value.paciente.dni,
                                        enfermedades = viewModel.uiState.value.paciente.enfermedades
                                    )
                                )
                            )
                        },
                        onValueEnfermedadChange = { value ->
                            viewModel.handleEvent(
                                DetallePacienteEvent.OnValueEnfermedadChange(
                                    Enfermedad(
                                        value
                                    )
                                )
                            )
                        },
                    )
                }
                Error(
                    viewModel.uiState.collectAsState().value.error
                ) { viewModel.handleEvent(DetallePacienteEvent.ErrorCatch) }
            }
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
fun ContentPaciente(
    paciente: Paciente,
    enfermedad: Enfermedad,
    modifier: Modifier,
    onClickUpdate: (Paciente) -> Unit,
    onClickAdd: (Enfermedad) -> Unit,
    onValuePacienteChange: (String) -> Unit,
    onValueEnfermedadChange: (String) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                NameField(paciente, onValuePacienteChange)
                UpdateButton(onClick = onClickUpdate, modifier = Modifier, paciente = paciente)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "DNI: ", modifier = Modifier.padding(7.dp))
                Text(text = paciente.dni, modifier = Modifier.padding(7.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                EnfermedadField(
                    enfermedad = enfermedad,
                    onValueChange = onValueEnfermedadChange
                )
                AddButton(onClick = onClickAdd, modifier = Modifier, enfermedad = enfermedad)
            }
            ListEnfermedades(enfermedades = paciente.enfermedades, modifier = Modifier)
        }
    }
}

@Composable
fun NameField(paciente: Paciente, onValueChange: (String) -> Unit) {
    OutlinedTextField(value = paciente.nombre,
        label = { Text(text = "Nombre") },
        onValueChange = {
            onValueChange(it)
        },
        modifier = Modifier
            .wrapContentHeight()
            .padding(7.dp),
        leadingIcon = {
            Icon(
                Icons.Default.Person,
                modifier = Modifier.size(25.dp),
                contentDescription = "Name"
            )
        })
}

@Composable
fun EnfermedadField(enfermedad: Enfermedad, onValueChange: (String) -> Unit) {
    OutlinedTextField(value = enfermedad.nombre,
        label = { Text(text = "Enfermedad") },
        onValueChange = {
            onValueChange(it)
        },
        modifier = Modifier
            .wrapContentHeight()
            .padding(7.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_coronavirus_24),
                modifier = Modifier.size(25.dp),
                contentDescription = "Enfermedad"
            )
        })
}

@Composable
fun AddButton(onClick: (Enfermedad) -> Unit, modifier: Modifier, enfermedad: Enfermedad) {
    IconButton(
        onClick = { onClick(enfermedad) },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = "Agregar enfermedad"
        )
    }
}

@Composable
fun UpdateButton(
    onClick: (Paciente) -> Unit,
    paciente: Paciente,
    modifier: Modifier
) {
    IconButton(
        onClick = { onClick(paciente) },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Icon(Icons.Default.Refresh, contentDescription = "Actualizar nombre del paciente")
    }
}

@Composable
fun ListEnfermedades(enfermedades: List<Enfermedad>, modifier: Modifier) {
    LazyColumn {
        items(
            items = enfermedades,
            itemContent = {
                ItemsEnfermedades(it, modifier)
            },
        )
    }
}

@Composable
fun ItemsEnfermedades(enfermedad: Enfermedad, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = MaterialTheme.colors.error
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = enfermedad.nombre)
        }
    }
}



