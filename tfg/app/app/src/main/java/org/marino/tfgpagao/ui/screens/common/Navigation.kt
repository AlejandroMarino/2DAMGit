package org.marino.tfgpagao.ui.screens.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.marino.tfgpagao.ui.screens.groupCreation.GroupCreationScreen
import org.marino.tfgpagao.ui.screens.groups.GroupListScreen
import org.marino.tfgpagao.ui.screens.receipts.ReceiptListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "groupList",
    ) {
        composable(
            "groupList"
        ) {
            GroupListScreen(
                goReceiptList = { id, groupName -> navController.navigate("receiptList/$id/${groupName}") },
                goGroupCreation = { navController.navigate("groupCreation") }
            )
        }
        composable(
            "groupCreation"
        ) {
            GroupCreationScreen(
                {
                    TopBar(goBack = {
                        navController.popBackStack()
                    }, title = "Create a new group")
                },
                goGroupList = {
                    navController.navigate("groupList") {
                        popUpTo("groupList") {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            "receiptList/{groupId}/{groupName}",
            arguments = listOf(
                navArgument("groupId") {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("groupName") {
                    type = NavType.StringType
                }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            val groupName = it.arguments?.getString("groupName") ?: ""
            ReceiptListScreen(
                groupId = groupId,
                {
                    TopBar(goBack = {
                        navController.popBackStack()
                    }, title = groupName)
                }
            )
        }
    }
}