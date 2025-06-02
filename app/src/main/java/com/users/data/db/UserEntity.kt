package com.users.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val userId: String = "",
    val name: String = "",
    val cell: String = "",
    val mail: String = "",
    val thumbnail: String? = null,
    val picture: String? = null,
    val address: String = ""
)