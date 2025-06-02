package com.users.data.repository

import androidx.paging.PagingData
import com.users.data.db.UserEntity
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getUsers(): Flow<PagingData<UserEntity>>

    suspend fun getUserByUserId(userId:String): UserEntity?

}