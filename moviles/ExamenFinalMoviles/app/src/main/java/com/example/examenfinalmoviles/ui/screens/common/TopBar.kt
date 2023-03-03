package com.example.examenfinalmoviles.ui.screens.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun TopBar(
    goBack: () -> Unit,
    title: String,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { goBack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "go back"
                )
            }
        }
    )
}