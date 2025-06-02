package com.users.data.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.users.data.local.model.UserEntity
import com.users.data.local.UsersDatabase
import com.users.data.remote.UsersApi
import com.users.data.local.model.RemoteKeyEntity
import com.users.data.mapper.Mapper.mapUsersToEntity
import java.io.IOException
import java.util.concurrent.TimeUnit

@ExperimentalPagingApi
class UsersRemoteMediator(
    private val usersDb: UsersDatabase,
    private val usersApi: UsersApi
) : RemoteMediator<Int, UserEntity>() {

    private val remoteKeyDao = usersDb.remoteKeysDao
    private val usersDao = usersDb.dao

    override suspend fun initialize(): InitializeAction {
        val remoteKey = usersDb.withTransaction {
            remoteKeyDao.getKey("key")
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

        return if((System.currentTimeMillis() - remoteKey.lastUpdated) >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, UserEntity>): MediatorResult {
        return try {
            val page = when(loadType) {
                LoadType.REFRESH -> {
                    1
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val remoteKey = usersDb.withTransaction {
                        remoteKeyDao.getKey("key")
                    } ?: return MediatorResult.Success(true)

                    if(remoteKey.nextPage == null) {
                        return MediatorResult.Success(true)
                    }

                    remoteKey.nextPage
                }
            }

            val result = usersApi.getUsers(
                page = page,

            )

            usersDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    usersDao.clearAll()
                }

                val nextPage = if(result?.resultData.isNullOrEmpty()) {
                    null
                } else {
                    page+1
                }
                remoteKeyDao.insertKey(
                    RemoteKeyEntity(
                    id = "key",
                    nextPage = nextPage,
                    lastUpdated = System.currentTimeMillis()
                )
                )
                val userEntities = mapUsersToEntity(result)
                usersDb.dao.upsertAll(userEntities)

            }

            MediatorResult.Success(
                endOfPaginationReached = result?.resultData.isNullOrEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: retrofit2.HttpException) {
            MediatorResult.Error(e)
        }
    }
}