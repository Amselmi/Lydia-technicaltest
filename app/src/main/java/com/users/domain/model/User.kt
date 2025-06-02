package com.users.domain.model

data class User(
    val id: Int,
    val name: String = "",
    val cell: String = "",
    val mail: String = "",
    val thumbnail: String? = null,
    val userId: String = "",
    val picture: String? = null,
    val address: String = ""
)
