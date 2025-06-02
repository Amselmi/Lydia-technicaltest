package com.users.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.users.data.db.UserEntity
import com.users.data.db.UsersDatabase
import com.users.data.mapper.Mapper.mapUsersToEntity
import com.users.data.api.UsersApi
import java.io.IOException

@ExperimentalPagingApi
class UsersRemoteMediator(
    private val usersDb: UsersDatabase,
    private val usersApi: UsersApi
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }
            val users = usersApi.getUsers(
                page = loadKey
            )
            usersDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    usersDb.dao.clearAll()
                }
                val userEntities = mapUsersToEntity(users)
                usersDb.dao.upsertAll(userEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = users?.resultData?.isEmpty() == true
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: retrofit2.HttpException) {
            MediatorResult.Error(e)
        }
    }
}