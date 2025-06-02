package com.users.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nextPage: Int?,
    val lastUpdated: Long
)
