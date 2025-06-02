package com.users.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.users.ui.detail.DetailUserScreen
import com.users.ui.detail.DetailUserViewModel
import com.users.ui.detail.UserContract

@Composable
fun DetailUserDestination(userId: String, navController: NavController) {
    val viewModel =
        hiltViewModel<DetailUserViewModel,
                DetailUserViewModel.DetailUserViewModelFactory>
        { factory ->
            factory.create(userId)
        }
    val state = viewModel.viewState.collectAsStateWithLifecycle().value
    DetailUserScreen(
        state.user,
        onNavigationRequested = {
            if (it is UserContract.Action.Back) {
                navController.popBackStack()
            }
        }
    )
}
