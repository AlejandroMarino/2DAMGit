package org.marino.tfgpagao.ui.screens.groupCreation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GroupCreationScreen(
    topBar: @Composable () -> Unit,
    viewModel: GroupCreationViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = topBar,
            content = {
                if (state.value.isLoading) {
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

                    }
                }
                state.value.error?.let { it1 ->
                    Error(
                        it1
                    ) { viewModel.handleEvent(GroupCreationEvent.ErrorCatch) }
                }
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