package com.example.interview1

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun baseUrl(): String{
        return "https://bit.ly/"
    }

    @Provides
    @Singleton
    fun getRetrofit(baseUrl:String): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun getBookService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }

}