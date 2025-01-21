package com.example.todoapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.TodoItem

class EnterNewItemViewModel:ViewModel() {
    val posts: MutableState<List<TodoItem>> = mutableStateOf(emptyList())


}