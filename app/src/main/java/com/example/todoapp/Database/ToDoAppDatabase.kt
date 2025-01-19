package com.example.todoapp.Database

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.todoapp.model.TodoItem

@Database(entities = [TodoItem::class], version = 1)
abstract class ToDoAppDatabase:RoomDatabase() {
    abstract fun userDao():UserDao
}