package com.fakhour.todolist.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakhour.todolist.model.TaskModel
import com.fakhour.todolist.model.UserModel
import com.fakhour.todolist.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class TaskViewModel(private val repository: Repository) : ViewModel() {
    val myResponseCustomTask: MutableLiveData<Response<List<TaskModel>>> = MutableLiveData()
     var tasksListLiveData: LiveData<List<TaskModel>> = MutableLiveData()

    fun getCustomTasksFromDb(userId: Int) {
        tasksListLiveData= repository.getCustomTasksFromDb(userId)

    }

    fun getCustomTask(userId: Int) {
        viewModelScope.launch {
            val response = repository.getCustomTasks(userId)
            myResponseCustomTask.value = response
        }
    }

}