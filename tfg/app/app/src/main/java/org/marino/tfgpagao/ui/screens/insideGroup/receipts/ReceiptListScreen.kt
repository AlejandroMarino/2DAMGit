package org.marino.tfgpagao.ui.screens.insideGroup.receipts

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.marino.tfgpagao.R
import org.marino.tfgpagao.domain.model.Receipt

@Composable
fun ReceiptListScreen(
    groupId: Int,
    goReceiptCreation: (Int) -> Unit,
    goReceiptInfo: (Int) -> Unit,
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
                            Modifier,
                            receipts = state.value.receipts,
                            goReceiptInfo
                        )
                    }
                }
                state.value.error?.let { it1 ->
                    Error(
                        it1
                    ) { viewModel.handleEvent(ReceiptListEvent.ErrorCatch) }
                }
            },
            floatingActionButton = {
                ButtonCreateReceipt(goReceiptCreation, groupId)
            },
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
    goReceiptInfo: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = receipts, itemContent = { receipt ->
            ItemsReceipt(
                receipt = receipt,
                goReceiptInfo = goReceiptInfo,
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
    goReceiptInfo: (Int) -> Unit,
    modifier: Modifier
) {
    Card(
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 2.dp),
        onClick = { goReceiptInfo(receipt.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = receipt.name, fontSize = 18.sp
            )
            val formattedTotal = if (receipt.totalPaid != null && receipt.totalPaid % 1 == 0.0) {
                String.format("%.0f", receipt.totalPaid)
            } else {
                String.format("%.2f", receipt.totalPaid)
            }
            Text(
                text = "$formattedTotal €",
            )
        }
    }
}

@Composable
fun ButtonCreateReceipt(
    goReceiptCreation: (Int) -> Unit, groupId: Int
) {
    FloatingActionButton(onClick = { goReceiptCreation(groupId) }, containerColor = Color(0xFFA06E1D), contentColor = Color.Black) {
        Icon(
            painter = painterResource(R.drawable.ic_add_receipt_png),
            modifier = Modifier.size(30.dp),
            contentDescription = "Create new receipt"
        )
    }
}