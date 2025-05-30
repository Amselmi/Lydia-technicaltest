package com.users.data.model


import com.google.gson.annotations.SerializedName

data class UsersData(
    @SerializedName("info")
    val info: Info? = null,
    @SerializedName("results")
    val results: List<Result?>? = null
)