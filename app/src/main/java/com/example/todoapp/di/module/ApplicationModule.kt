package com.example.todoapp.module

import android.content.Context
import com.example.todoapp.ToDoApp
import com.example.todoapp.di.AppllicationContext
import com.example.todoapp.di.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
    object ApplicationModule {

    @AppllicationContext
    @Provides
    fun provideContext(application: ToDoApp): Context {
        return application
    }



}