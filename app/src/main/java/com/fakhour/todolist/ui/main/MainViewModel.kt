package com.fakhour.todolist.ui.main

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

class MainViewModel() : ViewModel() {


    var alreadyConnected : Int =-1


}