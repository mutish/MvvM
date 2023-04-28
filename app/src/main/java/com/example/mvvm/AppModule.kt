package com.example.mvvm

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

// this module is used to provide dependencies to other files in the Android app using the hilt D.I
// installin: specifies our class should be installed in the singletonComponent oF the HILT Class


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // method to provide the instance of the interface class that defines the http methods
    // the app uses to make requests.

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): UserService{
        return retrofit.create(UserService::class.java)
    }

}