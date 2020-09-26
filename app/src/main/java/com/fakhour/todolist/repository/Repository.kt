package com.fakhour.todolist.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.fakhour.todolist.api.RetrofitInstance
import com.fakhour.todolist.database.TodoDatabase
import com.fakhour.todolist.model.Post
import com.fakhour.todolist.model.TaskModel
import com.fakhour.todolist.model.UserModel
import retrofit2.Response
import java.util.*
import java.util.concurrent.Executors


private const val DATABASE_NAME = "todo-database"


class Repository private constructor(context: Context) {


    private val database: TodoDatabase = Room.databaseBuilder(
        context.applicationContext,
        TodoDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val userDao = database.userDao()
    private val taskDao = database.taskDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getUsersFromDb(): LiveData<List<UserModel>> {
        return userDao.getAllUsers()
    }


    fun getCustomTasksFromDb(id: Int): LiveData<List<TaskModel>> {
        return taskDao.getUserTasks(id)
    }

    fun getAllTasks(): LiveData<List<TaskModel>> {
        return taskDao.getAllTasks()
    }

    suspend fun getUsers(): Response<List<UserModel>> {


        val response = RetrofitInstance.api.getUsers()

        executor.execute {
            userDao.insertAllUsers(response.body()!!)

        }

        return response
    }

    suspend fun getCustomTasks(userId: Int): Response<List<TaskModel>> {
        val response = RetrofitInstance.api.getCustomTasks(userId)

        executor.execute {

            taskDao.insertUserTasks(response.body()!!)
            Log.d("RESPONSEyy", "ererereer")

        }


        return response
    }


    companion object {
        private var INSTANCE: Repository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = Repository(context)
            }
        }

        fun get(): Repository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}