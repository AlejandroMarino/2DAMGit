package org.marino.tfgpagao.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.marino.tfgpagao.ui.screens.common.components.CustomButton
import org.marino.tfgpagao.ui.screens.common.components.CustomTextField
import org.marino.tfgpagao.ui.screens.groupCreation.Error

@Composable
fun RegisterScreen(
    topBar: @Composable () -> Unit,
    goLogin: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
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
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        EmailIntroduction(
                            focusManager,
                            state.value.email,
                            state.value.emailIsValid
                        ) { newValue ->
                            viewModel.handleEvent(RegisterEvent.EmailChanged(newValue))
                        }
                        PasswordIntroduction(
                            focusManager,
                            state.value.password,
                            state.value.repeatedPassword,
                            state.value.coincidentPasswords,
                            { newValue ->
                                viewModel.handleEvent(RegisterEvent.PasswordChanged(newValue))
                            },
                            { newValue ->
                                viewModel.handleEvent(RegisterEvent.RepeatedPasswordChanged(newValue))
                            },
                        )
                        Spacer(Modifier.weight(1f))
                        BottomContent {
                            viewModel.handleEvent(RegisterEvent.DoRegister(goLogin))
                        }
                    }
                }
                state.value.error?.let { it1 ->
                    Error(
                        it1
                    ) { viewModel.handleEvent(RegisterEvent.ErrorCatch) }
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
fun EmailIntroduction(
    focusManager: FocusManager,
    email: String,
    isValidEmail: Boolean,
    onChangeEmail: (String) -> Unit
) {
    Column(Modifier.padding(top = 60.dp)) {
        Text("Email:", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        CustomTextField(
            focusManager = focusManager,
            text = email,
            placeholder = "Write Here",
            validation = isValidEmail,
            onChange = onChangeEmail
        )
    }
}

@Composable
fun PasswordIntroduction(
    focusManager: FocusManager,
    password: String,
    repeatedPassword: String,
    isValidPassword: Boolean,
    onChangePassword: (String) -> Unit,
    onChangeRepeatedPassword: (String) -> Unit,
) {
    Column(Modifier.padding(top = 60.dp)) {
        Text("Password:", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        CustomTextField(
            focusManager = focusManager,
            text = password,
            placeholder = "Write Here",
            validation = isValidPassword,
            onChange = onChangePassword,
            isPassword = true
        )
        CustomTextField(
            focusManager = focusManager,
            text = repeatedPassword,
            placeholder = "Repeat Password",
            validation = isValidPassword,
            onChange = onChangeRepeatedPassword,
            isPassword = true
        )
    }
}

@Composable
fun BottomContent(doRegister: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 60.dp),
        Alignment.Center
    ) {
        CustomButton("Register", doRegister)
    }
}