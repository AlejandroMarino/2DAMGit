package org.marino.tfgpagao.ui.screens.common

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.marino.tfgpagao.ui.screens.MainActivity
import org.marino.tfgpagao.ui.screens.groupCreation.GroupCreationScreen
import org.marino.tfgpagao.ui.screens.groups.GroupListScreen
import org.marino.tfgpagao.ui.screens.receipts.ReceiptListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = "groupList",
    ) {
        composable(
            "groupList"
        ) {
            GroupListScreen(
                goReceiptList = { id -> navController.navigate("receiptList/$id") },
                goGroupCreation = { navController.navigate("groupCreation") }
            )
        }
        composable(
            "groupCreation"
        ) {
            GroupCreationScreen(
                {
                    TopBar(goBack = {
                        context.startActivity(
                            Intent(
                                context,
                                MainActivity::class.java
                            )
                        )
                    }, title = "Create a new group")
                }
            )
        }
        composable(
            "receiptList/{groupId}",
            arguments = listOf(
                navArgument("groupId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            ReceiptListScreen(groupId = groupId)
        }
    }
}