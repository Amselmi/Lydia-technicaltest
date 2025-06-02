package com.users.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.users.data.local.UsersDatabase
import com.users.data.remote.UsersApi
import com.users.data.mapper.Mapper.mapUser
import com.users.data.remotemediator.UsersRemoteMediator
import com.users.domain.repository.Repository
import com.users.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val usersApi: UsersApi,
    private val usersDatabase: UsersDatabase
) : Repository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator = UsersRemoteMediator(
                usersDb = usersDatabase,
                usersApi = usersApi
            ),
            pagingSourceFactory = {
                usersDatabase.dao.pagingSource()
            }).flow
            .map { pagingData ->
                pagingData.map {
                    mapUser(it)
                }
            }

    }
    override suspend fun getUserByUserId(userId: String): User {
        return mapUser(usersDatabase.dao.getUser(userId))
    }
}

