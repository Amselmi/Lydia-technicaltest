package com.users.data.model


import com.google.gson.annotations.SerializedName

data class Timezone(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("offset")
    val offset: String? = null
)