package com.users.data.repository

import com.users.data.model.UsersData
import com.users.data.remote.UsersApi
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val usersApi: UsersApi
) : Repository {
    override suspend fun getUsers(page: Int): UsersData?{
        return usersApi.getUsers(page)

    }
}

