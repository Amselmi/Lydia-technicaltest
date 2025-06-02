package com.users.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.users.data.local.model.UserEntity

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(users: List<UserEntity>)

    @Query("SELECT * FROM userentity")
    fun pagingSource(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM userentity")
    suspend fun clearAll()

    @Query("SELECT * FROM userentity where userId IN (:userId)")
    suspend fun getUser(userId: String): UserEntity?
}