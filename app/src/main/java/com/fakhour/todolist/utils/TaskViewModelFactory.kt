package com.fakhour.todolist.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fakhour.todolist.ui.TaskViewModel
import com.fakhour.todolist.repository.Repository

class TaskViewModelFactory(private val repository:Repository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaskViewModel(repository) as T
    }
}