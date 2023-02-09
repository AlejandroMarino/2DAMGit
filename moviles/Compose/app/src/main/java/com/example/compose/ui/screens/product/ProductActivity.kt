package com.example.compose.ui.screens.product

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.example.compose.R
import com.example.compose.domain.modelo.Product
import com.example.compose.ui.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductActivity : ComponentActivity() {

    private val viewModel: ProductViewModel by viewModels()

    private var product = Product()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.viewModelScope.launch {
            viewModel.uiState.collect { state ->
                if (state.message.isNotBlank()) {
                    Toast.makeText(this@ProductActivity, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        setContent {
            ComposeTheme {
                Surface(
                    color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.padding(25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        NameField()
                        PriceField()
                        DescriptionField()
                        IconButton(
                            onClick = {
                                if (product.name.isBlank() || product.price == 0.0 || product.description.isBlank()) {
                                    Toast.makeText(this@ProductActivity, "Fill all fields", Toast.LENGTH_SHORT).show()
                                } else {
                                    addProduct(product)
                                }
                            },
                            modifier = Modifier
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
    }

    @Composable
    fun NameField() {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = text,
            label = { Text(text = "name") },
            onValueChange = {
                text = it
                product = product.copy(name = it.text)
            }, modifier = Modifier
                .wrapContentHeight()
                .padding(10.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_name_24),
                    modifier = Modifier.size(25.dp),
                    contentDescription = "name"
                )
            }
        )
    }

    @Composable
    fun PriceField() {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(value = text,
            label = { Text(text = "price") },
            onValueChange = {
                text = it
                product = product.copy(price = it.text.toDouble())
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .wrapContentHeight()
                .padding(10.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_euro_24),
                    modifier = Modifier.size(25.dp),
                    contentDescription = "euro"
                )
            })
    }

    @Composable
    fun DescriptionField() {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = text,
            label = { Text(text = "description") },
            maxLines = 5,
            onValueChange = {
                text = it
                product = product.copy(description = it.text)
            },
            modifier = Modifier
                .wrapContentHeight()
                .padding(10.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_description_24),
                    modifier = Modifier.size(25.dp),
                    contentDescription = "description"
                )
            }
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
                color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.padding(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    NameField()
                    PriceField()
                    DescriptionField()
                    IconButton(
                        onClick = {
                            if (product.name.isBlank() || product.price == 0.0 || product.description.isBlank()) {
                                Toast.makeText(this@ProductActivity, "Fill all fields", Toast.LENGTH_SHORT).show()
                            } else {
                                addProduct(product)
                            }
                        },
                        modifier = Modifier
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