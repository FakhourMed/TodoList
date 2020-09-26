package com.fakhour.todolist.ui

import android.app.Application
import androidx.lifecycle.*
import com.fakhour.todolist.model.UserModel
import com.fakhour.todolist.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import com.fakhour.todolist.utils.Network


class UsersViewModel(private val repository: Repository) : ViewModel() {
    val myResponseUsers: MutableLiveData<Response<List<UserModel>>> = MutableLiveData()

    // Tous les utilisateurs de la base de données
    val usersListLiveData: LiveData<List<UserModel>> = repository.getUsersFromDb()

    // Tous les utilisateurs du web
    fun getUsers() {

        viewModelScope.launch {
            val response = repository.getUsers()
            myResponseUsers.value = response
        }

    }
}




