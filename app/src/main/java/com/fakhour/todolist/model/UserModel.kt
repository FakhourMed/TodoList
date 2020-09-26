package com.fakhour.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserModel(
    @PrimaryKey
    var id: Int,
    var name: String,
    var username: String,
    var email: String
)