package com.example.mvvm

import retrofit2.http.GET

interface UserService {
    // http requests(get, post , update and delete)
    @GET("/users")
    suspend fun fetchUsers(): List<User>
}