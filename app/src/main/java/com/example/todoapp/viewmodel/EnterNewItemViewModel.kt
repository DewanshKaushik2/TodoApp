package com.example.todoapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.DatabaseClient
import com.example.todoapp.model.TodoItem
import kotlinx.coroutines.launch

class EnterNewItemViewModel:ViewModel() {
    val posts: MutableState<List<TodoItem>> = mutableStateOf(emptyList())

    init {
        //d getData()
    }

   /* fun getData() {
//        ToDoApp.getInstance()
        viewModelScope.launch {
           val data= DatabaseClient.getInstance(coroutineContext)?.getAppDatabase()
                ?.userDao()
                ?.getAllUsers();
            if (data != null) {
                posts.value = data
            };
        }

    }*/

}