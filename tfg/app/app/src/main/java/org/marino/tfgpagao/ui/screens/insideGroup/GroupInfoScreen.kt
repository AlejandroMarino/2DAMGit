package org.marino.tfgpagao.ui.screens.insideGroup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.marino.tfgpagao.ui.screens.insideGroup.balances.BalanceListScreen
import org.marino.tfgpagao.ui.screens.insideGroup.receipts.ReceiptListScreen

@Composable
fun GroupInfoScreen(
    groupId: Int,
    goReceiptCreation: (Int) -> Unit,
    goReceiptCreationPredefined: (Int, Int, Int, Float) -> Unit,
    goReceiptInfo: (Int) -> Unit,
    topBar: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = topBar,
            content = {
                val tabs = listOf("Receipts", "Balances")
                var selectedTabIndex by remember { mutableIntStateOf(0) }
                Column(modifier = Modifier.padding(it)) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier.fillMaxWidth(),
                        divider = {},
                        contentColor = Color(0xFFA06E1D),
                        indicator = {tabPositions ->
                            Box(
                                Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                    .height(4.dp)
                                    .fillMaxWidth()
                                    .background(Color(0xFFA06E1D), shape = RoundedCornerShape(2.dp))
                            )
                        }
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                text = {
                                    Text(text = title)
                                },
                                onClick = {
                                    selectedTabIndex = index
                                }
                            )
                        }
                    }

                    when (selectedTabIndex) {
                        0 -> ReceiptListScreen(groupId = groupId, goReceiptCreation, goReceiptInfo)
                        1 -> BalanceListScreen(groupId = groupId, goReceiptCreationPredefined)
                    }

                }
            }
        )
    }
}