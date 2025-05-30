package com.users.data.repository

import com.users.data.model.UsersData

interface Repository {

    suspend fun getUsers(page: Int): UsersData?

}