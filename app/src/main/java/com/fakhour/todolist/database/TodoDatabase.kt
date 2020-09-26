package com.fakhour.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fakhour.todolist.model.TaskModel
import com.fakhour.todolist.model.UserModel

@Database(entities = [ UserModel::class, TaskModel::class ], version=2)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao
}
