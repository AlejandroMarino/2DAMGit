package org.marino.tfgpagao.ui.screens.groupCreation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.marino.tfgpagao.R
import org.marino.tfgpagao.ui.model.MemberVO

@Composable
fun GroupCreationScreen(
    topBar: @Composable () -> Unit,
    goGroupList: () -> Unit,
    viewModel: GroupCreationViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

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
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        NameIntroduction(focusManager, state.value.name) { newName ->
                            viewModel.handleEvent(
                                GroupCreationEvent.NameChanged(newName)
                            )
                        }
                        DescriptionIntroduction(state.value.description) { newDescription ->
                            viewModel.handleEvent(
                                GroupCreationEvent.DescriptionChanged(newDescription)
                            )
                        }
                        MembersIntroduction(
                            focusManager, state.value.members,
                            addMember = {
                                viewModel.handleEvent(
                                    GroupCreationEvent.MemberAdded
                                )
                            },
                            changeMemberName = { index: Int, newMemberName: String ->
                                viewModel.handleEvent(
                                    GroupCreationEvent.MemberNameChanged(index, newMemberName)
                                )
                            },
                            deleteMember = { index ->
                                viewModel.handleEvent(
                                    GroupCreationEvent.MemberDeleted(index)
                                )
                            }
                        )
                    }
                }
                state.value.error?.let { it1 ->
                    Error(
                        it1
                    ) { viewModel.handleEvent(GroupCreationEvent.ErrorCatch) }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { viewModel.handleEvent(GroupCreationEvent.AddGroup(goGroupList)) },
                    containerColor = Color(0xFFA06E1D),
                    contentColor = Color.Black
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_save_group_png),
                        modifier = Modifier.size(30.dp),
                        contentDescription = "Create group"
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
        textAlign = TextAlign.Center,
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
            TextTitleOfFields("Name", Modifier.align(Alignment.CenterHorizontally))
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
            TextTitleOfFields("Description", Modifier.align(Alignment.CenterHorizontally))
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
fun MembersIntroduction(
    focusManager: FocusManager,
    members: List<MemberVO>,
    changeMemberName: (Int, String) -> Unit,
    addMember: () -> Unit,
    deleteMember: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextTitleOfFields("Members", Modifier.align(Alignment.CenterHorizontally))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .heightIn(max = 300.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                itemsIndexed(members) { index, member ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Gray)
                            .padding(start = 8.dp)
                    ) {
                        val dotColor = when {
                            member.valid -> Color(0xFF60CE61)
                            else -> Color(0xFFF36B6B)
                        }
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            if (member.valid) {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = "valid",
                                    tint = dotColor,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .align(Alignment.Center)
                                )
                            } else {
                                Icon(
                                    painter = painterResource(R.drawable.ic_circled_x),
                                    contentDescription = "invalid",
                                    tint = dotColor,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                        TextField(
                            value = member.name,
                            onValueChange = { newValue ->
                                if (newValue.length <= 20) {
                                    changeMemberName(index, newValue)
                                }
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                            singleLine = true,
                            modifier = Modifier
                                .weight(5f)
                                .padding(bottom = 0.dp)
                                .padding(start = 10.dp),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Gray,
                                focusedContainerColor = Color.LightGray,
                                focusedIndicatorColor = Color.Black,
                                unfocusedIndicatorColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            )
                        )
                        if (members.size > 1) {
                            IconButton(
                                onClick = {
                                    deleteMember(index)
                                    focusManager.clearFocus()
                                },
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.CenterVertically)
                                    .weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete member",
                                    tint = Color.Black,
                                )
                            }
                        }

                    }
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
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-4).dp)
                    ) {
                        Button(
                            onClick = { addMember() },
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
                            Text("Add Member")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Cosas() {
    var members by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Gray)
                .padding(start = 8.dp)
        ) {
            val isValid = members.isNotEmpty()
            val dotColor = when {
                isValid -> Color.Green
                else -> Color.Red
            }
            Box(
                modifier = Modifier
                    .size(30.dp)
//                    .background(color = dotColor, shape = CircleShape)
                    .align(Alignment.CenterVertically)
//                    .padding(horizontal = 8.dp)
            ) {
                if (isValid) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "valid",
                        tint = dotColor,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_circled_x),
                        contentDescription = "invalid",
                        tint = dotColor,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            TextField(
                value = members,
                onValueChange = { newValue ->
                    if (newValue.length <= 20) {
                        members = newValue
                    }
                },
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                singleLine = true,
                modifier = Modifier
                    .weight(5f)
                    .padding(bottom = 0.dp)
                    .padding(start = 8.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Gray,
                    focusedContainerColor = Color.LightGray,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                ),
            )
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete member",
                    tint = Color.Black,
                )
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-2).dp)
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
                .offset(y = (-6).dp)
        ) {
            Button(
                onClick = { },
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
                Text("Add Member")
            }
        }

    }
}
