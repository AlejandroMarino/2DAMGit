package com.example.compose2.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose2.R
import com.example.compose2.domain.modelo.Product
import com.example.compose2.ui.Navigator

@Composable
fun ListActivity(
    navigator: Navigator,
    viewModel: ListViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(ListEvent.GetProducts)
    }
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            content = {
                List(
                    modifier = Modifier.padding(it),
                    viewModel.uiState.collectAsState().value.products
                )
            },
            floatingActionButton = {
                AddButton(navigator)
            },
        )
    }
}

@Composable
fun List(modifier: Modifier, products: List<Product>) {
    LazyColumn(contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_m))) {
        items(items = products, itemContent = {
            ProductItem(it, modifier)
        })
    }
}

@Composable
fun ProductItem(product: Product, modifier: Modifier) {
    Card(
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 7.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column {
            ItemTitle(product)
            Divider(
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(horizontal = 7.dp)
            )
            ItemDescription(product)
        }

    }
}

@Composable
fun ItemTitle(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = product.name[0].uppercaseChar() + product.name.substring(1),
            style = MaterialTheme.typography.h6,
        )
        Text(
            text = product.price.toString() + " " + stringResource(id = R.string.euro_symbol),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ItemDescription(product: Product) {
    Row(horizontalArrangement = Arrangement.Center) {
        Text(
            text = product.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 7.dp),
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun AddButton(navigator: Navigator) {
    FloatingActionButton(onClick = { navigator.navigateToProduct() }) {
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
