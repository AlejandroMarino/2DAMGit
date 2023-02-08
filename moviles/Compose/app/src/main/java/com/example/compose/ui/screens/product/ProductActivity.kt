package com.example.compose.ui.screens.product

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.domain.modelo.Product
import com.example.compose.ui.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : ComponentActivity() {

    private val viewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    TextField("Android")
                }
            }
        }
    }


    @Composable
    fun TextField(name: String) {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(value = text, label = { Text(text = name) }, onValueChange = {
            text = it
        }, modifier = Modifier
            .wrapContentHeight()
            .padding(10.dp)
        )
    }

    @Composable
    fun NumberField(name: String) {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(value = text, label = { Text(text = name) }, onValueChange = {
            text = it
        }, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ), modifier = Modifier
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
                    NumberField("Price")
                    TextField("Description")

                    IconButton(
                        onClick = { addProduct(Product(name = "eeeeeeeeeeeeeeeeeeeeeeee")) }, modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.End)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = MaterialTheme.colors.primary,
                                    shape = MaterialTheme.shapes.small
                                )
                        )
                    }
                }
            }
        }
    }

    private fun addProduct(product: Product) {
        viewModel.handleEvent(ProductEvent.AddProduct(product))
    }

}