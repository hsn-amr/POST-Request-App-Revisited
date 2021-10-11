package com.example.postrequestpractice

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @Headers("Content-Type: application/json")
    @GET("/test/")
    fun getUsers(): Call<List<User.UserInfo>?>?

    @Headers("Content-Type: application/json")
    @POST("/test/")
    fun addUser(@Body userData: User): Call<User>

    @Headers("Content-Type: application/json")
    @PUT("/test/{id}") // PUT replaces the full object (use PATCH to change individual fields)
    fun updateUser(@Path("id") id: Int, @Body userData: User.UserInfo): Call<User.UserInfo>

    @Headers("Content-Type: application/json")
    @DELETE("/test/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Void> //we use "Void" to overwrite an existing post
}