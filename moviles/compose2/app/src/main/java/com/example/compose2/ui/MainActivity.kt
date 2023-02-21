package com.example.compose2.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose2.ui.screens.list.ListActivity
import com.example.compose2.ui.screens.list.ListViewModel
import com.example.compose2.ui.screens.product.ProductActivity
import com.example.compose2.ui.screens.product.ProductViewModel
import com.example.compose2.ui.theme.Compose2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModelList: ListViewModel by viewModels()
    private val viewModelProduct: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            val navigator = Navigator(navController)
            Compose2Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background){
                    NavHost(navController = navController, startDestination = "list") {
                        composable("list") {
                            ListActivity(navigator)
                        }
                        composable("product") {
                            ProductActivity(navigator)
                        }
                    }
                }
            }
        }
    }

}