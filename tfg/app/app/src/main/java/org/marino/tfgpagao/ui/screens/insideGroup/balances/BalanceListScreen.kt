package org.marino.tfgpagao.ui.screens.insideGroup.balances

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.marino.tfgpagao.R
import org.marino.tfgpagao.domain.model.Member
import org.marino.tfgpagao.ui.screens.insideGroup.receipts.Error

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceListScreen(
    groupId: Int,
    goReceiptCreationPredefined: (Int, Int, Int, Float) -> Unit,
    viewModel: BalanceListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    var isBottomSheetVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(BalanceListEvent.GetMembers(groupId))
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
                        ListBalances(
                            Modifier,
                            members = state.value.members
                        )
                    }
                }
                state.value.error?.let { it1 ->
                    Error(
                        it1
                    ) { viewModel.handleEvent(BalanceListEvent.ErrorCatch) }
                }
            },
            floatingActionButton = {
                if (state.value.transactions.isNotEmpty() && !isBottomSheetVisible) {
                    FloatingActionButton(
                        onClick = { isBottomSheetVisible = true },
                        containerColor = Color(0xFFA06E1D),
                        contentColor = Color.Black
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_deal_png),
                            contentDescription = "Settle Debts"
                        )
                    }
                }
            }
        )
        if (isBottomSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = { isBottomSheetVisible = false },
                content = {
                    DebtsSettlement(
                        groupId,
                        transactions = state.value.transactions,
                        goReceiptCreationPredefined
                    )
                }
            )
        }
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
fun ListBalances(
    modifier: Modifier,
    members: List<Member>,
) {
    LazyColumn(modifier = modifier) {
        items(items = members, itemContent = { member ->
            ItemsBalance(
                member = member,
                modifier = Modifier.animateItemPlacement(
                    animationSpec = tween(1000)
                )
            )
        })
    }
}

@Composable
fun ItemsBalance(
    member: Member,
    modifier: Modifier
) {
    Card(
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = member.name,
            )
            val formattedTotal = if (member.balance != null && member.balance % 1 == 0.0) {
                String.format("%.0f", member.balance)
            } else {
                String.format("%.2f", member.balance)
            }
            var color = Color.Black

            if (member.balance != null) {
                color = if (member.balance < 0) {
                    Color.Red
                } else if (member.balance > 0) {
                    Color.Green
                } else {
                    Color.White
                }
            }

            Text(
                text = "$formattedTotal €",
                color = color
            )
        }
    }
}

@Composable
fun DebtsSettlement(
    groupId: Int,
    transactions: List<Pair<Member, Member>>,
    goReceiptCreationPredefined: (Int, Int, Int, Float) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Suggested transactions",
            style = MaterialTheme.typography.headlineSmall,
            textDecoration = TextDecoration.Underline
        )
        LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
            items(items = transactions, itemContent = { transaction ->
                TransactionItem(groupId, transaction, goReceiptCreationPredefined)
                Spacer(modifier = Modifier.height(8.dp))
            })
        }
    }
}

@Composable
fun TransactionItem(
    groupId: Int,
    transaction: Pair<Member, Member>,
    goReceiptCreationPredefined: (Int, Int, Int, Float) -> Unit
) {
    val payer = transaction.first
    val receiver = transaction.second
    val formattedAmount = if (receiver.balance != null && receiver.balance % 1 == 0.0) {
        String.format("%.0f", receiver.balance)
    } else {
        String.format("%.2f", receiver.balance)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                goReceiptCreationPredefined(
                    groupId,
                    payer.id,
                    receiver.id,
                    formattedAmount.toFloat()
                )
            }
    ) {
        Text(
            text = payer.name,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 20.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_pay_png),
                contentDescription = "pays"
            )

            Text(
                text = "$formattedAmount €",
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Icon(
                painter = painterResource(R.drawable.baseline_double_arrow_24),
                tint = Color(0xFF1FB320),
                contentDescription = "to"
            )
        }

        Text(
            text = receiver.name,
            modifier = Modifier.weight(1f)
        )
    }

    HorizontalDivider(modifier = Modifier.padding(horizontal = 30.dp))

}