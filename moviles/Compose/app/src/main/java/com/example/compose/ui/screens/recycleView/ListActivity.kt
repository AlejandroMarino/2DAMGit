package com.example.compose.ui.screens.recycleView

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.domain.modelo.Product
import com.example.compose.ui.screens.product.ProductActivity
import com.example.compose.ui.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : ComponentActivity() {

    private val viewModel: ListViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.handleEvent(ListEvent.GetProducts)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier.padding(top = 50.dp)) {
                        List()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(onClick = { changeActivity() }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Favorite",
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colors.primaryVariant,
                                            shape = RoundedCornerShape(50)
                                        )
                                        .padding(vertical = 10.dp, horizontal = 30.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun List() {
        val state by viewModel.uiState.collectAsState()
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(
                items = state.products,
                itemContent = {
                    ProductItem(product = it)
                }
            )
        }
    }

    @Composable
    fun ProductItem(product: Product) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            elevation = 8.dp,
            backgroundColor = MaterialTheme.colors.primaryVariant,
            shape = RoundedCornerShape(corner = CornerSize(16.dp))
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = product.name,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = product.price.toString() + " €", modifier = Modifier
                            .padding(16.dp)
                            .padding(top = 5.dp)
                    )
                }
                Row {
                    Divider(
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.padding(horizontal = 7.dp)
                    )
                }
                Row {
                    Text(
                        text = product.description,
                        modifier = Modifier.padding(
                            top = 5.dp,
                            bottom = 7.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }

    private fun changeActivity() {
        startActivity(Intent(this, ProductActivity::class.java))
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
                Column(modifier = Modifier.padding(top = 50.dp)) {
                    ProductItem(
                        product = Product(
                            name = "Salchichon",
                            price = 5.0,
                            description = "Está mu rico es de Huelva"
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = { changeActivity() }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "add",
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colors.primaryVariant,
                                        shape = RoundedCornerShape(corner = CornerSize(16.dp))
                                    )
                                    .padding(vertical = 10.dp, horizontal = 30.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}