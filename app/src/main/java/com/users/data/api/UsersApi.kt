package com.users.data.api

import com.users.data.Endpoints
import com.users.data.model.UsersData
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET(Endpoints.GET_USERS)
    suspend fun getUsers(@Query("page") page: Int): UsersData?

}