package org.marino.tfgpagao.ui.screens.receipts

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.marino.tfgpagao.domain.model.Receipt

@Composable
fun ReceiptListScreen(
    groupId: Int,
    viewModel: ReceiptListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(ReceiptListEvent.GetReceipts(groupId))
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
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
                        ListReceipts(
                            Modifier.weight(2f),
                            receipts = state.value.receipts
                        )
                    }
                }
                state.value.error?.let { it1 ->
                    Error(
                        it1
                    ) { viewModel.handleEvent(ReceiptListEvent.ErrorCatch) }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListReceipts(
    modifier: Modifier,
    receipts: List<Receipt>,
) {
    LazyColumn(modifier = modifier) {
        items(items = receipts, itemContent = { receipt ->
            ItemsReceipt(
                receipt = receipt,
                modifier = Modifier.animateItemPlacement(
                    animationSpec = tween(1000)
                )
            )
        })
    }
}

@Composable
fun ItemsReceipt(
    receipt: Receipt,
    modifier: Modifier
) {
    Card(
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Text(
                text = receipt.name,
            )
        }

    }
}