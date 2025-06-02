package com.users.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.users.ui.navigation.Navigation.Args.USER_ID

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Navigation.Routes.USERS
    ) {
        composable(
            route = Navigation.Routes.USERS
        ) {
            UsersDestination(navController)
        }

        composable(
            route = Navigation.Routes.USER,
            arguments = listOf(navArgument(name = USER_ID) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val userId =
                requireNotNull(backStackEntry.arguments?.getString(USER_ID))
                { "User id is required as an argument" }
            DetailUserDestination(
                userId = userId,
                navController = navController
            )
        }
    }
}
