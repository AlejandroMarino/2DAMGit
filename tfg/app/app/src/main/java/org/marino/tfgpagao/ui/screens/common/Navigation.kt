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
import org.marino.tfgpagao.ui.screens.login.LoginScreen
import org.marino.tfgpagao.ui.screens.receipCreation.ReceiptCreationScreen
import org.marino.tfgpagao.ui.screens.receiptInfo.ReceiptInfoScreen
import org.marino.tfgpagao.ui.screens.register.RegisterScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login",
    ) {
        composable(
            "login"
        ) {
            LoginScreen(
                goRegister = {
                    navController.navigate("register")
                },
                goGroups = {
                    navController.navigate("groupList") {
                        popUpTo("groupList") {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            "register"
        ) {
            RegisterScreen(
                {
                    TopBar(
                        goBack = {
                            navController.popBackStack()
                        },
                        title = "Register"
                    )
                },
                goLogin = {
                    navController.navigate("login") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

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
                    TopBar(
                        goBack = {
                            navController.popBackStack()
                        },
                        title = "Create a new group"
                    )
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
                    navController.navigate("receiptCreation/$id")
                },
                goReceiptCreationPredefined = { id, idPayer, idReceiver, amount ->
                    navController.navigate("receiptCreation/$id?payerId=$idPayer&receiverId=$idReceiver&amount=$amount")
                },
                goReceiptInfo = { id ->
                    navController.navigate("receiptInfo/$id")
                }
            ) {
                TopBar(
                    goBack = {
                        navController.popBackStack()
                    },
                    title = groupName
                )
            }
        }
        composable(
            "receiptCreation/{groupId}?payerId={payerId}&receiverId={receiverId}&amount={amount}",
            arguments = listOf(
                navArgument("groupId") {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("payerId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("receiverId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("amount") {
                    type = NavType.FloatType
                    defaultValue = -1
                }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            val payerId = it.arguments?.getInt("payerId") ?: -1
            val receiverId = it.arguments?.getInt("receiverId") ?: -1
            val amount = it.arguments?.getFloat("amount") ?: -1
            ReceiptCreationScreen(
                {
                    TopBar(
                        goBack = {
                            navController.popBackStack()
                        },
                        title = "Create a new receipt"
                    )
                },
                goGroupInfo = {
                    navController.popBackStack()
                },
                groupId = groupId,
                payerId = payerId,
                receiverId = receiverId,
                amount = amount.toDouble()
            )
        }
        composable(
            "receiptInfo/{receiptId}",
            arguments = listOf(
                navArgument("receiptId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            val receiptId = it.arguments?.getInt("receiptId") ?: 0
            ReceiptInfoScreen(
                receiptId = receiptId,
                {
                    TopBar(
                        goBack = {
                            navController.popBackStack()
                        },
                        title = ""
                    )
                }
            )
        }
    }
}