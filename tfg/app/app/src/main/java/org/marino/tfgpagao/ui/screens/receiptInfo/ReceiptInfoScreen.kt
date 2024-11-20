package org.marino.tfgpagao.ui.screens.receiptInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.marino.tfgpagao.R
import org.marino.tfgpagao.ui.model.ParticipationVO
import org.marino.tfgpagao.ui.screens.groupCreation.Error

@Composable
fun ReceiptInfoScreen(
    receiptId: Int,
    topBar: @Composable () -> Unit,
    viewModel: ReceiptInfoViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(ReceiptInfoEvent.LoadReceipt(receiptId))
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = topBar,
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
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Content(
                            state.value.receipt.name,
                            state.value.receipt.description,
                            state.value.participations
                        )
                    }
                }
                state.value.error?.let { it1 ->
                    Error(
                        it1
                    ) { viewModel.handleEvent(ReceiptInfoEvent.ErrorCatch) }
                }
            }
        )
    }
}

@Composable
fun Content(
    receiptName: String,
    receiptDescription: String?,
    participations: List<ParticipationVO>
) {
    Column {
        ReceiptInfo(receiptName, receiptDescription, Modifier.align(Alignment.CenterHorizontally))
        ParticipationList(participations)
    }
}

@Composable
fun ReceiptInfo(receiptName: String, receiptDescription: String?, modifier: Modifier) {
    var isDialogOpen by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .padding(bottom = 20.dp)
            .then(
                if (!receiptDescription.isNullOrBlank()) {
                    Modifier.clickable { isDialogOpen = true }
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            TextTitleOfFields(receiptName, Modifier.align(Alignment.CenterHorizontally))
            if (!receiptDescription.isNullOrBlank()) {
                Icon(
                    painter = painterResource(R.drawable.ic_more_24),
                    contentDescription = "More",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
    if (isDialogOpen) {
        Dialog(
            onDismissRequest = { isDialogOpen = false },
        ) {
            Box(
                Modifier
                    .padding(bottom = 100.dp)
                    .background(Color(0x6A000000), shape = RoundedCornerShape(16.dp))
            ) {
                Text(
                    receiptDescription ?: "",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 20.dp)
                )
            }
        }
    }
}

@Composable
fun ParticipationList(participations: List<ParticipationVO>) {
    var selectedParticipation by remember { mutableStateOf<ParticipationVO?>(null) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(10.dp)
                .padding(top = 15.dp),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp,
            text = "Participation"
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .sizeIn(maxHeight = 700.dp),
            contentPadding = PaddingValues(horizontal = 15.dp)
        ) {
            itemsIndexed(
                participations,
                key = { _, participation -> participation.memberName }) { _, participation ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .then(
                            if (!participation.description.isNullOrBlank()) {
                                Modifier.clickable { selectedParticipation = participation }
                            } else {
                                Modifier
                            }
                        )
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                    ) {
                        Box(
                            Modifier
                                .weight(0.25f)
                                .fillMaxHeight()
                                .background(Color(0xFC454545)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                participation.pays.toString() + " €",
                                color = Color.Green,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(0.5f)
                                .fillMaxHeight()
                                .height(minOf(60.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(5.dp),
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 15.sp,
                                    text = participation.memberName
                                )
                                if (!participation.description.isNullOrBlank()) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_more_24),
                                        contentDescription = "More",
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                        Box(
                            Modifier
                                .weight(0.25f)
                                .fillMaxHeight()
                                .background(Color(0xFC454545)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                participation.debts.toString() + " €",
                                color = Color.Red,
                            )
                        }
                    }
                }
            }
        }
        selectedParticipation?.let { participation ->
            Dialog(
                onDismissRequest = { selectedParticipation = null },
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Color(0x6A000000), shape = RoundedCornerShape(16.dp))
                        .height(IntrinsicSize.Min)
                ) {
                    Text(
                        participation.memberName,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 50.dp, vertical = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        textDecoration = TextDecoration.Underline,
                        fontFamily = FontFamily.Serif,
                        fontSize = 20.sp,
                    )
                    Text(
                        participation.description ?: "",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .padding(bottom = 20.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }

    }
}

@Composable
fun TextTitleOfFields(text: String, modifier: Modifier) {
    Text(
        modifier = modifier
            .padding(5.dp)
            .padding(top = 15.dp),
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.Serif,
        fontSize = 30.sp,
        text = text
    )
}