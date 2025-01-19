package com.example.todoapp.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.todoapp.model.TodoItem

@Dao
interface UserDao {
    @Insert
     fun insertUser(user: TodoItem)

    @Query("SELECT * FROM itemtable")
     fun getAllUsers(): List<TodoItem>

    @Delete
     fun deleteUser(user: TodoItem)
}