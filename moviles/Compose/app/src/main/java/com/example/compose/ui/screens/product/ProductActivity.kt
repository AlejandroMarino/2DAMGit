package com.example.compose.ui.screens.product

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.ComposeTheme

class ProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TextField("Android")
                }
            }
        }
    }
}

@Composable
fun TextField(name: String) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = text,
        label = { Text(text = name) },
        onValueChange = {
            text = it
        },
        modifier = Modifier
            .wrapContentHeight()
            .padding(10.dp)
    )
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4,
    showSystemUi = true,
)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        Surface(
            color = MaterialTheme.colors.background,
        ) {
            Column(
                modifier = Modifier.padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField("Name")
                TextField("Price")
                TextField("Description")
            }
        }
    }
}