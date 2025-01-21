package com.example.todoapp.di.module

import android.content.Context
import com.example.todoapp.ToDoApp
import com.example.todoapp.di.AppllicationContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
    object ApplicationModule {

    @AppllicationContext
    @Provides
    fun provideContext(application: ToDoApp): Context {
        return application
    }


}