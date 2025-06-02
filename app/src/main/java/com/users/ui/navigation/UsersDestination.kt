package com.users.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.users.ui.home.UsersContract.*
import com.users.ui.home.UsersScreen
import com.users.ui.home.UsersViewModel

@Composable
fun UsersDestination(navController: NavController) {
    val viewModel = hiltViewModel<UsersViewModel>()

    val users = viewModel.viewState.collectAsStateWithLifecycle()
    UsersScreen(
        state = users,
        onNavigationRequested = {
            if (it is Action.ToDetail) {
                navController.navigateToDetailUser(it.userId)
            }
        }
    )
}
