package com.users.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.users.data.db.UserEntity
import com.users.data.db.UsersDatabase
import com.users.data.api.UsersApi
import com.users.data.paging.UsersRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val usersApi: UsersApi,
    private val usersDatabase: UsersDatabase
) : Repository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getUsers(): Flow<PagingData<UserEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            remoteMediator = UsersRemoteMediator(
                usersDb = usersDatabase,
                usersApi = usersApi
            ),
            pagingSourceFactory = {
                usersDatabase.dao.pagingSource()
            }).flow
    }

    override suspend fun getUserByUserId(userId: String): UserEntity? {
        return usersDatabase.dao.getUser(userId)
    }
}

