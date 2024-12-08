package org.marino.tfgpagao.ui.screens.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(title: String, action: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(top = 10.dp)
            .size(134.dp, 50.dp)
            .clip(RoundedCornerShape(16.dp)),
        shape = RectangleShape,
        onClick = { action() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFA06E1D),
            contentColor = Color.Black
        )
    ) {
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    }
}