package com.users.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.users.data.local.dao.RemoteKeyDao
import com.users.data.local.dao.UsersDao
import com.users.data.local.model.RemoteKeyEntity
import com.users.data.local.model.UserEntity

@Database(
    entities = [UserEntity::class,
        RemoteKeyEntity::class],
    version = 1
)
abstract class UsersDatabase : RoomDatabase() {

    abstract val dao: UsersDao
    abstract val remoteKeysDao: RemoteKeyDao

}