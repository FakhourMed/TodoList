package com.fakhour.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fakhour.todolist.model.UserModel
import java.util.*

@Dao
interface UserDao {

    @Query("SELECT * FROM usermodel")
    fun getAllUsers() : LiveData<List<UserModel>>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(userList: List<UserModel>)

    @Query("DELETE FROM usermodel")
    fun deleteAllUsers()
}