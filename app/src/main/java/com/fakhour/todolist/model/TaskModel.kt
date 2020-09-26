package com.fakhour.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TaskModel(
    val userId: Int,
    @PrimaryKey
    val id : Int,
    val title: String,
    val completed: String
)