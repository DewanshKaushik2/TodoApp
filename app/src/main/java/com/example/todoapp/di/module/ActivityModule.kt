package com.example.todoapp.di.module


import android.content.Context
import com.example.todoapp.database.DatabaseClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ActivityModule {

    @Provides
    fun providedatabaseClient(@ApplicationContext context: Context): DatabaseClient {
        return DatabaseClient.getInstance(context)!!
    }
}