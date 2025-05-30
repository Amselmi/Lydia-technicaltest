package com.users.intent

sealed class UsersIntent {

    data class GetUsers(var page: Int) : UsersIntent()
    data class GetUserDetails(var id: Int) : UsersIntent()


}