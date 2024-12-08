package org.marino.tfgpagao.ui.screens.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.marino.tfgpagao.R

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    text: String,
    validation: Boolean = true,
    onChange: (String) -> Unit,
    placeholder: String = "",
    isPassword: Boolean = false,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier
            .width(250.dp)
            .padding(5.dp),
        maxLines = 1,
        value = text,
        onValueChange = {
            if (it.length <= 20) {
                onChange(it)
            }
        },
        visualTransformation = if (!isPassword || isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = { Text(placeholder) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (!validation || text.isEmpty()) Color(0xFFF36B6B) else Color(
                0xFF60CE61
            ),
            unfocusedBorderColor = if (!validation || text.isEmpty()) Color(0xFF9C3636) else Color(
                0xFF3E8D46
            ),
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        trailingIcon = {
            if (isPassword) {
                val image = if (isPasswordVisible)
                    painterResource(R.drawable.ic_visibility_24)
                else painterResource(R.drawable.ic_visibility_off_24)

                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(painter = image, contentDescription = null, tint = Color.Gray)
                }
            }
        }
    )
}