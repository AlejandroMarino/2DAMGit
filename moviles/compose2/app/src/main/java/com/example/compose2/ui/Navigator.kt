package com.example.compose2.ui

import androidx.navigation.NavHostController

class Navigator(private val navController: NavHostController) {

    fun navigateToList() {
        navController.navigate("list")
    }

    fun navigateToProduct() {
        navController.navigate("product")
    }
}