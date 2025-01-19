package com.example.todoapp.model

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.todoapp.Database.DatabaseClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class Repository@Inject constructor(
   @ApplicationContext private val context: Context,
    private val dbHelper: DatabaseClient,
) {

    suspend fun getData(): Flow<Any> {
        return flow {
            val data = DatabaseClient.getInstance(context)?.getAppDatabase()
                ?.userDao()
                ?.getAllUsers();
            if (data != null) {
                emit(data)
            };

        }
    }
}