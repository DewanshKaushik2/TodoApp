package com.example.todoapp.database

import android.content.Context
import androidx.room.Room.databaseBuilder
import javax.inject.Singleton

@Singleton
class DatabaseClient private constructor(mCtx: Context) {
    //our app database object
    private val appDatabase: ToDoAppDatabase

    init {

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = databaseBuilder(mCtx, ToDoAppDatabase::class.java, "MyToDos").build()
    }

    fun getAppDatabase(): ToDoAppDatabase {
        return appDatabase
    }

    companion object {
        private var mInstance: DatabaseClient? = null
        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient? {
            if (mInstance == null) {
                mInstance = DatabaseClient(mCtx)
            }
            return mInstance
        }
    }
}