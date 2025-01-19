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
public class MainViewModel @Inject constructor(var repository: Repository) : ViewModel() {
    val todolist: MutableState<List<TodoItem>> = mutableStateOf(emptyList())

    init {
        //d prepareList()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getData().collect {
                todolist.value = it as List<TodoItem>;
            }
        }
    }

    fun prepareList() {
        var list = ArrayList<TodoItem>();
       val todo= TodoItem(0,"first");
        list.add(todo)
        list.add(todo)
        list.add(todo)
        list.add(todo)
        list.add(todo)
        for (i in 0..100) {
            list.add(todo)
        }
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getData().collect {
                todolist.value = it as List<TodoItem>;
            }
        }
    }

}