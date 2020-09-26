package com.fakhour.todolist.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fakhour.todolist.ui.UsersViewModel
import com.fakhour.todolist.repository.Repository

class UsersViewModelFactory(private val repository:Repository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersViewModel(repository) as T
    }
}