package com.example.todoapp.model

import com.example.todoapp.database.DatabaseClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class Repository@Inject constructor(
    private val databaseClient: DatabaseClient,
) {

    suspend fun getData(): Flow<Any> {
        return flow {
            val data = databaseClient.getAppDatabase()
                .itemDao()
                .getAllItems();
            emit(data)
        }
    }
}