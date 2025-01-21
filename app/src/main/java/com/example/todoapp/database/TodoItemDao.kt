package com.example.todoapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todoapp.model.TodoItem

@Dao
interface TodoItemDao {
    @Insert
     fun insertItem(user: TodoItem)

    @Query("SELECT * FROM itemtable")
     fun getAllItems(): List<TodoItem>
}