package com.users.domain.repository

import androidx.paging.PagingData
import com.users.domain.model.User
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getUsers(): Flow<PagingData<User>>

    suspend fun getUserByUserId(userId:String): User

}