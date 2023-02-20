package com.example.compose.ui.screens.product

import android.content.Intent
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import com.example.compose.R
import com.example.compose.domain.modelo.Product
import com.example.compose.ui.screens.list.ListActivity
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
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        TopAppBar(
                            title = { Text(text = getString(R.string.add_product)) },
                            navigationIcon = {
                                IconButton(onClick = { changeActivity() }) {
                                    Icon(
                                        Icons.Default.ArrowBack,
                                        contentDescription = getString(R.string.add)
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .align(Alignment.CenterHorizontally)
                        )
                        Column(
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.size_medium)),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            NameField()
                            PriceField()
                            DescriptionField()
                            IconButton(
                                onClick = {
                                    if (product.name.isBlank() || product.price == 0.0 || product.description.isBlank()) {
                                        Toast.makeText(
                                            this@ProductActivity,
                                            getString(R.string.fill_all_fields),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        addProduct(product)
                                    }
                                }, modifier = Modifier
                                    .padding(dimensionResource(id = R.dimen.padding_m))
                                    .align(Alignment.End)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = stringResource(R.string.add),
                                    modifier = Modifier
                                        .size(dimensionResource(id = R.dimen.padding_xxl))
                                        .background(
                                            color = MaterialTheme.colors.primary,
                                            shape = MaterialTheme.shapes.small,
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun changeActivity() {
        startActivity(Intent(this, ListActivity::class.java))
    }

    @Composable
    fun NameField() {
        var text by remember { mutableStateOf(TextFieldValue(getString(R.string.empty))) }
        OutlinedTextField(value = text,
            label = { Text(text = getString(R.string.name)) },
            onValueChange = {
                text = it
                product = product.copy(name = it.text)
            },
            modifier = Modifier
                .wrapContentHeight()
                .padding(dimensionResource(id = R.dimen.padding_s)),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_name_24),
                    modifier = Modifier.size(dimensionResource(id = R.dimen.size_medium)),
                    contentDescription = stringResource(R.string.name)
                )
            })
    }

    @Composable
    fun PriceField() {
        var text by remember { mutableStateOf(TextFieldValue(getString(R.string.empty))) }
        OutlinedTextField(value = text,
            label = { Text(text = getString(R.string.price)) },
            onValueChange = {
                val price =
                    it.text.replace(getString(R.string.coma), getString(R.string.dot)).replace(
                        getString(R.string.minus), getString(R.string.empty)
                    )
                text = it.copy(text = price)
                product = if (price.isBlank()) {
                    product.copy(price = 0.0)
                } else {
                    product.copy(price = price.toDouble())
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .wrapContentHeight()
                .padding(dimensionResource(id = R.dimen.padding_s)),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_euro_24),
                    modifier = Modifier.size(dimensionResource(id = R.dimen.size_medium)),
                    contentDescription = stringResource(R.string.euro)
                )
            })
    }

    @Composable
    fun DescriptionField() {
        var text by remember { mutableStateOf(TextFieldValue(getString(R.string.empty))) }
        OutlinedTextField(value = text,
            label = { Text(text = getString(R.string.description)) },
            maxLines = 5,
            onValueChange = {
                text = it
                product = product.copy(description = it.text)
            },
            modifier = Modifier
                .wrapContentHeight()
                .padding(dimensionResource(id = R.dimen.padding_s)),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_description_24),
                    modifier = Modifier.size(dimensionResource(id = R.dimen.size_medium)),
                    contentDescription = stringResource(R.string.description)
                )
            })
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TopAppBar(
                        title = { Text(text = getString(R.string.add_product)) },
                        navigationIcon = {
                            IconButton(onClick = { changeActivity() }) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = stringResource(R.string.back)
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .align(Alignment.CenterHorizontally)
                    )
                    Column(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.size_medium)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        NameField()
                        PriceField()
                        DescriptionField()
                        IconButton(
                            onClick = {
                                if (product.name.isBlank() || product.price == 0.0 || product.description.isBlank()) {
                                    Toast.makeText(
                                        this@ProductActivity,
                                        getString(R.string.fill_all_fields),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    addProduct(product)
                                }
                            }, modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.padding_s))
                                .align(Alignment.End)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.add),
                                modifier = Modifier
                                    .size(dimensionResource(id = R.dimen.padding_xl))
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

    private fun addProduct(product: Product) {
        viewModel.handleEvent(ProductEvent.AddProduct(product))
    }

}