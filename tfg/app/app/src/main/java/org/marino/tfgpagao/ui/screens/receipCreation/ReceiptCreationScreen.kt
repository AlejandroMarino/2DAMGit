package org.marino.tfgpagao.ui.screens.receipCreation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.marino.tfgpagao.R
import org.marino.tfgpagao.ui.model.MemberReceiptVO
import org.marino.tfgpagao.ui.screens.groupCreation.Error

@Composable
fun ReceiptCreationScreen(
    topBar: @Composable () -> Unit,
    groupId: Int,
    payerId: Int,
    receiverId: Int,
    amount: Double,
    goGroupInfo: () -> Unit,
    viewModel: ReceiptCreationViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(
            ReceiptCreationEvent.LoadMembersOfGroup(
                groupId,
                payerId,
                receiverId,
                amount
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
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
                            .padding(bottom = 80.dp)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        NameIntroduction(focusManager, state.value.receipt.name) { newName ->
                            viewModel.handleEvent(ReceiptCreationEvent.ChangeReceiptName(newName))
                        }
                        DescriptionIntroduction(
                            state.value.receipt.description ?: ""
                        ) { newDescription ->
                            viewModel.handleEvent(
                                ReceiptCreationEvent.ChangeReceiptDescription(
                                    newDescription
                                )
                            )
                        }
                        Payers(
                            focusManager = focusManager,
                            balance = state.value.receipt.totalPaid ?: 0.0,
                            selectedPayers = state.value.selectedPayers,
                            availablePayers = state.value.availablePayers,
                            showOrHideDescription = { index: Int, showedDescription: Boolean ->
                                viewModel.handleEvent(
                                    ReceiptCreationEvent.ShowOrHidePayerDescription(
                                        index,
                                        showedDescription
                                    )
                                )
                            },
                            changePayerSelected = { index: Int, member: MemberReceiptVO ->
                                viewModel.handleEvent(
                                    ReceiptCreationEvent.ChangePayerSelected(index, member)
                                )
                            },
                            changePayerDescription = { index: Int, description: String ->
                                viewModel.handleEvent(
                                    ReceiptCreationEvent.ChangePayerDescription(index, description)
                                )
                            },
                            changePayerAmount = { index: Int, amount: Double ->
                                viewModel.handleEvent(
                                    ReceiptCreationEvent.ChangePayerAmount(index, amount)
                                )
                            },
                            addPayer = { viewModel.handleEvent(ReceiptCreationEvent.AddPayer) },
                            deletePayer = { index: Int ->
                                viewModel.handleEvent(ReceiptCreationEvent.DeletePayer(index))
                            },
                        )
                        Debtors(
                            focusManager = focusManager,
                            balance = state.value.receipt.totalPaid ?: 0.0,
                            selectedDebtors = state.value.selectedDebtors,
                            availableDebtors = state.value.availableDebtors,
                            showOrHideDescription = { index: Int, showedDescription: Boolean ->
                                viewModel.handleEvent(
                                    ReceiptCreationEvent.ShowOrHideDebtorDescription(
                                        index,
                                        showedDescription
                                    )
                                )
                            },
                            addDebtor = { viewModel.handleEvent(ReceiptCreationEvent.AddDebtor) },
                            deleteDebtor = { index: Int ->
                                viewModel.handleEvent(ReceiptCreationEvent.DeleteDebtor(index))
                            },
                            changeDebtorDescription = { index: Int, description: String ->
                                viewModel.handleEvent(
                                    ReceiptCreationEvent.ChangeDebtorDescription(
                                        index,
                                        description
                                    )
                                )
                            },
                            changeDebtorSelected = { index: Int, member: MemberReceiptVO ->
                                viewModel.handleEvent(
                                    ReceiptCreationEvent.ChangeDebtorSelected(
                                        index,
                                        member
                                    )
                                )
                            },
                            changeDebtorAmount = { index: Int, amount: Double ->
                                viewModel.handleEvent(
                                    ReceiptCreationEvent.ChangeDebtorAmount(
                                        index,
                                        amount
                                    )
                                )
                            }
                        )
                    }
                }
                state.value.error?.let { it1 ->
                    Error(
                        it1
                    ) { viewModel.handleEvent(ReceiptCreationEvent.ErrorCatch) }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { viewModel.handleEvent(ReceiptCreationEvent.AddReceipt(goGroupInfo)) },
                    containerColor = Color(0xFFA06E1D),
                    contentColor = Color.Black
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_save_payment_png),
                        modifier = Modifier.size(30.dp),
                        contentDescription = "save receipt"
                    )
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

@Composable
fun TextTitleOfFields(text: String, modifier: Modifier) {
    Text(
        modifier = modifier
            .padding(5.dp)
            .padding(top = 15.dp),
        fontFamily = FontFamily.Serif,
        fontSize = 25.sp,
        text = text
    )
}

@Composable
fun NameIntroduction(focusManager: FocusManager, name: String, changeName: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            TextTitleOfFields(
                "Name",
                Modifier.align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                modifier = Modifier
                    .width(250.dp)
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 1,
                value = name,
                onValueChange = {
                    if (it.length <= 20) {
                        changeName(it)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (name.isEmpty()) Color(0xFFF36B6B) else Color(0xFF60CE61),
                    unfocusedBorderColor = if (name.isEmpty()) Color(0xFF9C3636) else Color(
                        0xFF3E8D46
                    ),
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )
        }
    }
}

@Composable
fun DescriptionIntroduction(description: String, changeDescription: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            TextTitleOfFields(
                "Description",
                Modifier.align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                modifier = Modifier
                    .width(250.dp)
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 4,
                value = description,
                onValueChange = {
                    if (it.length <= 100) {
                        changeDescription(it)
                    }
                },
                placeholder = { Text("(Optional)") }
            )
        }
    }
}

@Composable
fun Payers(
    focusManager: FocusManager,
    balance: Double,
    selectedPayers: List<MemberReceiptVO>,
    availablePayers: List<MemberReceiptVO>,
    showOrHideDescription: (index: Int, descriptionShowed: Boolean) -> Unit,
    changePayerSelected: (index: Int, member: MemberReceiptVO) -> Unit,
    changePayerDescription: (index: Int, description: String) -> Unit,
    changePayerAmount: (index: Int, amount: Double) -> Unit,
    addPayer: () -> Unit,
    deletePayer: (index: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Row {
            GroupTitle("Payers")
            GroupBalance(balance)
        }
        MembersList(
            focusManager = focusManager,
            isPayer = true,
            selectedMembers = selectedPayers,
            availableMembers = availablePayers,
            showOrHideDescription = showOrHideDescription,
            changeAmount = changePayerAmount,
            changeDescription = changePayerDescription,
            changeMemberSelected = changePayerSelected,
            addMember = addPayer,
            deleteMember = deletePayer,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun Debtors(
    focusManager: FocusManager,
    balance: Double,
    selectedDebtors: List<MemberReceiptVO>,
    availableDebtors: List<MemberReceiptVO>,
    showOrHideDescription: (index: Int, descriptionShowed: Boolean) -> Unit,
    changeDebtorSelected: (index: Int, member: MemberReceiptVO) -> Unit,
    changeDebtorDescription: (index: Int, description: String) -> Unit,
    changeDebtorAmount: (index: Int, amount: Double) -> Unit,
    addDebtor: () -> Unit,
    deleteDebtor: (index: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Row {
            GroupTitle("Debtors")
            GroupBalance(-balance)
        }
        MembersList(
            focusManager = focusManager,
            isPayer = false,
            selectedMembers = selectedDebtors,
            availableMembers = availableDebtors,
            showOrHideDescription = showOrHideDescription,
            changeAmount = changeDebtorAmount,
            changeDescription = changeDebtorDescription,
            changeMemberSelected = changeDebtorSelected,
            addMember = addDebtor,
            deleteMember = deleteDebtor,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun GroupTitle(title: String) {
    Text(
        modifier = Modifier
            .padding(15.dp)
            .padding(top = 15.dp),
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.Serif,
        fontSize = 25.sp,
        text = title
    )
}

@Composable
fun GroupBalance(balance: Double) {
    fun formatAmount(amount: Double): String {
        return amount.let {
            if (it % 1 == 0.0) it.toInt().toString() else it.toString()
        }
    }

    val amount = formatAmount(balance)
    val textColor = when {
        balance < 0 -> Color(0xFFF36B6B)
        balance > 0 -> Color(0xFF60CE61)
        else -> Color.White
    }
    if (balance < 0 || balance > 0) {
        Text(
            modifier = Modifier
                .padding(15.dp)
                .padding(top = 15.dp),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Serif,
            fontSize = 25.sp,
            text = amount,
            color = textColor
        )
    }
}

@Composable
fun MembersList(
    focusManager: FocusManager,
    isPayer: Boolean,
    selectedMembers: List<MemberReceiptVO>,
    availableMembers: List<MemberReceiptVO>,
    addMember: () -> Unit,
    deleteMember: (Int) -> Unit,
    changeDescription: (Int, String) -> Unit,
    changeAmount: (Int, Double) -> Unit,
    changeMemberSelected: (Int, MemberReceiptVO) -> Unit,
    showOrHideDescription: (Int, Boolean) -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .clip(RoundedCornerShape(16.dp))
            .sizeIn(maxHeight = 300.dp)
    ) {
        itemsIndexed(selectedMembers, key = { _, member -> member.name }) { index, member ->

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                ) {
                    BoxIconShowDescription(
                        member = member,
                        index = index,
                        showOrHideDescription = showOrHideDescription,
                        modifier = Modifier
                            .defaultMinSize(40.dp)
                            .weight(0.1f)
                            .align(Alignment.CenterVertically)
                    )
                    MemberSelector(
                        member = member,
                        availableMembers = availableMembers,
                        changeSelected = changeMemberSelected,
                        index = index,
                        modifier = Modifier
                            .weight(0.3f)
                            .defaultMinSize(minWidth = 100.dp)
                    )
                    AmountTextField(
                        focusManager = focusManager,
                        member = member,
                        changeAmount = changeAmount,
                        index = index,
                        modifier = Modifier
                            .weight(0.5f)
                            .defaultMinSize(minWidth = 150.dp)
                            .padding(start = 10.dp)
                            .background(color = Color.Gray)
                    )
                    if (selectedMembers.size > 1) {
                        IconDelete(
                            focusManager = focusManager,
                            delete = deleteMember,
                            index = index,
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.CenterVertically)
                                .weight(0.1f)
                        )
                    }
                }
                if (member.showingDescription) {
                    DescriptionBox(member, changeDescription, index)
                }
                if (index < selectedMembers.count() - 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-1).dp)
                    ) {
                        HorizontalDivider(
                            color = Color.Black,
                            thickness = 2.dp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

        }
        if (availableMembers.isNotEmpty()) {
            item {
                ContainerButtonAdd(isPayer, addMember)
            }
        }
    }
}

@Composable
fun BoxIconShowDescription(
    member: MemberReceiptVO,
    index: Int,
    showOrHideDescription: (Int, Boolean) -> Unit,
    modifier: Modifier
) {
    val iconColor = when {
        member.showingDescription -> Color(0xFFF36B6B)
        else -> Color(0xFF60CE61)
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (member.showingDescription) {
            IconButton(
                onClick = {
                    showOrHideDescription(index, false)
                }
            ) {
                Icon(
                    Icons.Default.KeyboardArrowUp,
                    tint = iconColor,
                    contentDescription = "Hide",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        } else {
            IconButton(
                onClick = {
                    showOrHideDescription(index, true)
                }
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    tint = iconColor,
                    contentDescription = "Show",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberSelector(
    member: MemberReceiptVO,
    availableMembers: List<MemberReceiptVO>,
    changeSelected: (Int, MemberReceiptVO) -> Unit,
    index: Int,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .height(56.dp),
            value = member.name,
            onValueChange = {},
            textStyle = TextStyle(textAlign = TextAlign.Center),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Gray,
                focusedContainerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black
            ),
            readOnly = true
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            availableMembers.forEach { member ->
                DropdownMenuItem(
                    onClick = {
                        changeSelected(index, member)
                        expanded = false
                    },
                    text = {
                        Text(text = member.name)
                    }
                )
            }
        }
    }
}

@Composable
fun AmountTextField(
    focusManager: FocusManager,
    modifier: Modifier,
    member: MemberReceiptVO,
    changeAmount: (Int, Double) -> Unit,
    index: Int
) {
    fun formatAmount(amount: Double): String {
        return amount.let {
            if (it % 1 == 0.0) it.toInt().toString() else it.toString()
        }
    }

    var amount by remember { mutableStateOf(formatAmount(member.amount)) }
    TextField(
        modifier = modifier
            .onFocusChanged { focusState ->
                if (!focusState.isFocused) {
                    val parsed: Double? = if (amount.isBlank()) {
                        0.0
                    } else {
                        amount.toDoubleOrNull()
                    }
                    if (parsed != null) {
                        changeAmount(index, parsed)
                        amount = formatAmount(parsed)
                    } else {
                        amount = formatAmount(member.amount)
                    }
                }
            },
        value = amount,
        onValueChange = { newValue ->
            var validInput = newValue.replace(",", ".")

//            validInput.trimStart('0').ifEmpty { "0" }
            validInput =
                if (validInput.startsWith("0") && validInput.length > 1 && validInput[1] != '.') {
                    validInput.trimStart('0')
                } else {
                    validInput
                }

            val decimalIndex = validInput.indexOf(".")
            if (decimalIndex != -1 && validInput.length > decimalIndex + 3) {
                validInput = validInput.substring(0, decimalIndex + 3)
            }

            amount = validInput
        },
//        placeholder = {
//            Text(
//                "0",
//                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.End
//            )
//        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Decimal
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        singleLine = true,
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_euro),
                contentDescription = "Euro"
            )
        },
        textStyle = TextStyle(textAlign = TextAlign.End),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Gray,
            focusedContainerColor = Color.LightGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedPlaceholderColor = Color.Black,
            focusedPlaceholderColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Color.Black,
            focusedTrailingIconColor = Color.Black
        )
    )
}

@Composable
fun IconDelete(modifier: Modifier, focusManager: FocusManager, delete: (Int) -> Unit, index: Int) {
    IconButton(
        onClick = {
            delete(index)
            focusManager.clearFocus()
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete member",
            tint = Color.Black,
        )
    }
}

@Composable
fun DescriptionBox(
    member: MemberReceiptVO,
    changeDescription: (Int, String) -> Unit,
    index: Int
) {
    Box(modifier = Modifier.background(Color.Gray)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = member.description,
            onValueChange = { changeDescription(index, it) },
            placeholder = { Text("Description") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Gray,
                focusedContainerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                unfocusedPlaceholderColor = Color.LightGray,
                focusedPlaceholderColor = Color.Black,
            ),
        )
    }
}

@Composable
fun ContainerButtonAdd(isPayer: Boolean, action: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-1).dp)
        ) {
            HorizontalDivider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-4).dp)
        ) {
            if (isPayer) {
                ButtonAdd(action, "Add Payer")
            } else {
                ButtonAdd(action, "Add Debtor")
            }
        }
    }
}

@Composable
fun ButtonAdd(action: () -> Unit, text: String) {
    Button(
        onClick = { action() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp),
        colors = ButtonColors(
            containerColor = Color.Gray,
            contentColor = Color.Black,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color.LightGray
        ),
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomEnd = 20.dp,
            bottomStart = 20.dp
        )
    ) {
        Text(text)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Preview() {
    val members = listOf(MemberReceiptVO("Paco"), MemberReceiptVO("Pedro"))
    var selected by remember { mutableStateOf(members[0]) }
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            value = selected.name,
            onValueChange = { },
            readOnly = true
        )
        ExposedDropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }
        ) {
            members.forEachIndexed { index, member ->
                DropdownMenuItem(
                    onClick = {
//                    changePayerSelected(index, member)
                        selected = members[index]
                        expanded = false
                    },
                    text = {
                        Text(text = member.name)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
