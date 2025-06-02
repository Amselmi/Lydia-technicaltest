package com.users.ui.navigation

import androidx.navigation.NavController
import com.users.ui.navigation.Navigation.Args.USER_ID

object Navigation {

    object Args {
        const val USER_ID = "user_id"
    }

    object Routes {
        const val USERS = "users"
        const val USER = "$USERS/{$USER_ID}"
    }
}

fun NavController.navigateToDetailUser(userId: String) {
    navigate(route = "${Navigation.Routes.USERS}/$userId")
}
