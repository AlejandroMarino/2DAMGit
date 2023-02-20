package com.example.compose2.ui.screens.product

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.compose2.R
import com.example.compose2.domain.modelo.Product
import com.example.compose2.ui.Navigator

@Composable
fun ProductActivity(viewModel: ProductViewModel, navigator: Navigator) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            topBar = { TopBar(navigator) },
            content = { Content(viewModel, Modifier.padding(it)) },
            floatingActionButton = {
                AddButton(viewModel, viewModel.uiState.collectAsState().value.product)
            },
        )
    }
}

@Composable
fun TopBar(navigator: Navigator) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.add_product)) },
        navigationIcon = {
            IconButton(onClick = { navigator.navigateToList() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }
    )
}

@Composable
fun Content(viewModel: ProductViewModel, modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.size_medium))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NameField(
            name = viewModel.uiState.collectAsState().value.product.name,
            onTextFieldChanged = {
                viewModel.handleEvent(
                    ProductEvent.OnValueChanged(
                        Product(
                            name = it,
                            price = viewModel.uiState.value.product.price,
                            description = viewModel.uiState.value.product.description
                        )
                    )
                )
            })
        Spacer(modifier = Modifier.height(16.dp))
        PriceField(
            onPriceFieldChanged = {
                viewModel.handleEvent(
                    ProductEvent.OnValueChanged(
                        Product(
                            name = viewModel.uiState.value.product.name,
                            price = it,
                            description = viewModel.uiState.value.product.description
                        )
                    )
                )
            })
        Spacer(modifier = Modifier.height(16.dp))
        DescriptionField(description = viewModel.uiState.collectAsState().value.product.description,
            onTextFieldChanged = {
                viewModel.handleEvent(
                    ProductEvent.OnValueChanged(
                        Product(
                            name = viewModel.uiState.value.product.name,
                            price = viewModel.uiState.value.product.price,
                            description = it
                        )
                    )
                )
            })
    }
}

@Composable
fun NameField(name: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(value = name,
        label = { Text(text = stringResource(id = R.string.name)) },
        onValueChange = {
            onTextFieldChanged(it)
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
fun PriceField(onPriceFieldChanged: (Double) -> Unit) {
    val coma = stringResource(id = R.string.coma)
    val dot = stringResource(id = R.string.dot)
    val minus = stringResource(id = R.string.minus)
    val empty = stringResource(id = R.string.empty)
    var text by remember {
        mutableStateOf(TextFieldValue(""))
    }
    OutlinedTextField(value = text,
        label = { Text(text = stringResource(id = R.string.price)) },
        onValueChange = {
            var price = it.text.replace(coma, dot).replace(minus, empty).replace(" ", empty)
            if (price.isNotEmpty()) {
                if (price[0] == dot[0]) {
                    price = it.text.replaceFirst(dot, empty)
                }
                if (price[0] == coma[0]) {
                    price = it.text.replaceFirst(coma, empty)
                }
            }
            text = it.copy(text = price)
            if (price.isBlank()) {
                onPriceFieldChanged(0.0)
            } else {
                onPriceFieldChanged(price.toDouble())
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
fun DescriptionField(description: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(value = description,
        label = { Text(text = stringResource(id = R.string.description)) },
        maxLines = 5,
        onValueChange = {
            onTextFieldChanged(it)
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

@Composable
private fun AddButton(viewModel: ProductViewModel, product: Product) {
    val context = LocalContext.current
    val message = stringResource(R.string.fill_all_fields)
    FloatingActionButton(onClick = {
        if (product.name.isBlank() || product.price == 0.0 || product.description.isBlank()) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModel.handleEvent(ProductEvent.AddProduct(product))
        }
    }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add),
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(50)
                )
                .padding(
                    vertical = 16.dp,
                    horizontal = 30.dp
                )
        )
    }
}

