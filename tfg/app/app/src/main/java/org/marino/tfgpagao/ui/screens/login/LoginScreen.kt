package org.marino.tfgpagao.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.marino.tfgpagao.R
import org.marino.tfgpagao.ui.screens.common.components.CustomButton
import org.marino.tfgpagao.ui.screens.common.components.CustomTextField
import org.marino.tfgpagao.ui.screens.groupCreation.Error

@Composable
fun LoginScreen(
    goRegister: () -> Unit,
    goGroups: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
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
                        Logo()
                        Fields(
                            focusManager,
                            state.value.email,
                            state.value.emailIsValid,
                            state.value.password,
                            { newEmail ->
                                viewModel.handleEvent(LoginEvent.EmailChanged(newEmail))
                            },
                            { newPassword ->
                                viewModel.handleEvent(LoginEvent.PasswordChanged(newPassword))
                            }
                        )
                        Spacer(Modifier.weight(1f))
                        BottomContent(
                            goRegister,
                            doLogin = {
                                viewModel.handleEvent(LoginEvent.DoLogin(goGroups))
                            }
                        )
                    }
                }
                state.value.error?.let { it1 ->
                    Error(
                        it1
                    ) { viewModel.handleEvent(LoginEvent.ErrorCatch) }
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
fun Logo() {
    Image(
        painter = painterResource(R.drawable.iconpagao),
        contentDescription = "Logo",
        modifier = Modifier.padding(vertical = 50.dp)
    )
}

@Composable
fun Fields(
    focusManager: FocusManager,
    email: String,
    isValidEmail: Boolean,
    password: String,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.align(Alignment.Center)) {
            CustomTextField(
                Modifier.padding(bottom = 20.dp),
                focusManager,
                email,
                isValidEmail,
                onChangeEmail,
                "Email",
            )
            CustomTextField(
                focusManager = focusManager,
                text = password,
                onChange = onChangePassword,
                placeholder = "Password",
                isPassword = true
            )
        }
    }
}

@Composable
fun BottomContent(goRegister: () -> Unit, doLogin: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 60.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier.clickable { goRegister() }) {
                Text(text = "Aren't you registered? ")
                Text(text = "Register", textDecoration = TextDecoration.Underline)
            }
            CustomButton("Login", doLogin)
        }
    }
}
