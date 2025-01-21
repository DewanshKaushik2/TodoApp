package com.example.todoapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Repository
import com.example.todoapp.model.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var repository: Repository) : ViewModel() {
    var todolist: MutableState<List<TodoItem>> = mutableStateOf(emptyList())

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getData().collect {
                todolist.value = it as List<TodoItem>
            }
        }
    }

}