package com.chapter8.tugaschallange8.di

import com.chapter8.tugaschallange8.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //base URL user API
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val BASE_URL1 = "https://6254436219bc53e2347b9583.mockapi.io/"

    private val logging: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }
    private val client = OkHttpClient.Builder().addInterceptor(logging).build()

    //retrofit and api services module for film
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideApiServices(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    @Named("RetrofitUser")
    fun provideRetrofitForUserApi(): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL1)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    @Named("ApiServiceUser")
    fun provideApiServicesForUser(@Named("RetrofitUser") retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}