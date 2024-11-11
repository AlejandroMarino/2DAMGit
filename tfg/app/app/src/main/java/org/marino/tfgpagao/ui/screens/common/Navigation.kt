package org.marino.tfgpagao.ui.screens.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.marino.tfgpagao.ui.screens.groupCreation.GroupCreationScreen
import org.marino.tfgpagao.ui.screens.groups.GroupListScreen
import org.marino.tfgpagao.ui.screens.insideGroup.GroupInfoScreen
import org.marino.tfgpagao.ui.screens.receipCreation.ReceiptCreationScreen

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
                goGroupInfo = { id, groupName -> navController.navigate("groupInfo/$id/${groupName}") },
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
            "groupInfo/{groupId}/{groupName}",
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
            GroupInfoScreen(
                groupId = groupId,
                goReceiptCreation = { id ->
                    navController.navigate("receiptCreation/$id/$groupName")
                }
            ) {
                TopBar(goBack = {
                    navController.popBackStack()
                }, title = groupName)
            }
        }
//        composable(
//            "receiptList/{groupId}",
//            arguments = listOf(
//                navArgument("groupId") {
//                    type = NavType.IntType
//                    defaultValue = 0
//                },
//            )
//        ) {
//            val groupId = it.arguments?.getInt("groupId") ?: 0
//            ReceiptListScreen(
//                groupId = groupId,
//                goReceiptCreation = { id ->
//                    navController.navigate("receiptCreation/$id")
//                }
//            )
//        }
        composable(
            "receiptCreation/{groupId}/{groupName}",
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
            ReceiptCreationScreen(
                {
                    TopBar(goBack = {
                        navController.popBackStack()
                    }, title = "Create a new receipt")
                },
                goGroupInfo = { id, nameOfGroup ->
                    navController.navigate("groupInfo/$id/$nameOfGroup") {
                        popUpTo("groupList") {
                            inclusive = true
                        }
                    }
                },
                groupId = groupId,
                groupName = groupName
            )
        }
    }
}