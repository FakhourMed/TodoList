package com.fakhour.todolist.utils

import android.app.Application
import com.fakhour.todolist.repository.Repository

class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Repository.initialize(this)
    }
}