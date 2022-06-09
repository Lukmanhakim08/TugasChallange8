package com.chapter8.tugaschallange8.network

import com.chapter8.tugaschallange8.model.DataMovieResponse
import com.chapter8.tugaschallange8.model.DataUserResponseItem
import com.chapter8.tugaschallange8.model.PostUser
import com.chapter8.tugaschallange8.model.RequestUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getMovie(
        @Query("api_key") apiKey: String
    ): Call<DataMovieResponse>

    //user
    @GET("user")
    suspend fun getAllUser() : List<DataUserResponseItem>

    @POST("user")
    fun addNewUser(@Body requestUser: RequestUser) : Call<PostUser>

}