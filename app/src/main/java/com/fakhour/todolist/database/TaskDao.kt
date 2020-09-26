package com.fakhour.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fakhour.todolist.model.TaskModel
import com.fakhour.todolist.model.UserModel
import java.util.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM taskmodel")
    fun getAllTasks(): LiveData<List<TaskModel>>

    @Query("SELECT * FROM taskmodel WHERE userId=(:userId)")
    fun getUserTasks(userId: Int): LiveData<List<TaskModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserTasks(tasksList: List<TaskModel>)

    @Query("DELETE FROM taskmodel")
    fun deleteAllTasks()

}