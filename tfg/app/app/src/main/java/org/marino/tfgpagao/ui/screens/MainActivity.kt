package org.marino.tfgpagao.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import org.marino.tfgpagao.ui.screens.common.Navigation
import org.marino.tfgpagao.ui.theme.TfgPagaoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TfgPagaoTheme {
                Surface {
                    Navigation()
                }
            }
        }
    }
}
