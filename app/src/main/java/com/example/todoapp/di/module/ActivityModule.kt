package com.example.todoapp.module


import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import com.example.todoapp.Database.DatabaseClient
import com.example.todoapp.di.ActivityContext
import com.example.todoapp.di.MainActivityQualifier
import com.example.todoapp.di.MyActivityQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ActivityModule {

    @ActivityContext
    @Provides
    fun provideContext(@ApplicationContext activity: ComponentActivity): ComponentActivity {
        return activity
    }

    /*

          @Provides
          fun provideTopHeadlineViewModel(@ActivityContext context: AppCompatActivity
           , topHeadlineRepository: TopHeadlineRepository): MainViewModel {
              return ViewModelProvider(context,
                  ViewModelProviderFactory(MainViewModel::class) {
                      MainViewModel(topHeadlineRepository)
                  }
              )[MainViewModel::class.java]
        }

        @Provides
        fun provideFlowViewModel(activity: AppCompatActivity,
                                 topHeadlineRepository: TopHeadlineRepository): FlowViewModel {
            return ViewModelProvider(activity,
                ViewModelProviderFactory(FlowViewModel::class) {
                    FlowViewModel(topHeadlineRepository)
                }
            )[FlowViewModel::class.java]
        }
    */

    @Provides
    @MainActivityQualifier
    fun provideMainActivity(activity: Activity): ComponentActivity {
        if (activity is ComponentActivity) {
            return activity
        } else {
            throw IllegalArgumentException("Provided activity is not an instance of MainActivity")
        }
    }

    @Provides
    @MyActivityQualifier
    fun provideMyActivity(activity: Activity): ComponentActivity {
        if (activity is ComponentActivity) {
            return activity
        } else {
            throw IllegalArgumentException("Provided activity is not an instance of MainActivity")
        }
    }

    @Provides
    fun providedatabaseClient(@ApplicationContext context: Context): DatabaseClient {
        return DatabaseClient.getInstance(context)!!;
    }



    //    @InstallIn(SingletonComponent::class)
//    @Module
//    class AppModule {
//    @Singleton
//    @Provides
//    fun provideMoviesRepository(moviesRepositoryImpl: TopHeadlineRepository): TopHeadlineRepository {
//        return moviesRepositoryImpl
//    }

    //    }
}