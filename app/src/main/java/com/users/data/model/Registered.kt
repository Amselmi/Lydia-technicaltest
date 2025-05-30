package com.users.data.model


import com.google.gson.annotations.SerializedName

data class Registered(
    @SerializedName("age")
    val age: Int? = null,
    @SerializedName("date")
    val date: String? = null
)