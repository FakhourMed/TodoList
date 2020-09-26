package com.fakhour.todolist.api

import com.fakhour.todolist.model.Post
import com.fakhour.todolist.model.TaskModel
import com.fakhour.todolist.model.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SimpleApi {

    @GET("users")
    suspend fun getUsers(): Response<List<UserModel>>

    @GET("todos")
    suspend fun getCustomTasks(@Query("userId") userId: Int): Response<List<TaskModel>>
}