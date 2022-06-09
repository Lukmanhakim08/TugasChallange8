package com.chapter8.tugaschallange8.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.chapter8.tugaschallange8.model.DataMovieResponse
import com.chapter8.tugaschallange8.model.Movie
import com.chapter8.tugaschallange8.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelMovie @Inject constructor(apiService: ApiService) : ViewModel() {
    private val movieState = MutableStateFlow(emptyList<Movie>())
    val dataMovieState : StateFlow<List<Movie>> get() = movieState
    private val api = apiService
    private val apikey = "38c63d3167f5d3b3dc87620291bc2b1d"

    init {
        val command = api.getMovie(apikey)
        command.enqueue(object : Callback<DataMovieResponse>{
            override fun onResponse(
                call: Call<DataMovieResponse>,
                response: Response<DataMovieResponse>
            ) {
                if (response.isSuccessful){
                    movieState.value = response.body()!!.results
                }else{
                    Log.e("view_model", response.message())
                }
            }

            override fun onFailure(call: Call<DataMovieResponse>, t: Throwable) {
                Log.e("view_model_error", t.message.toString())
            }

        })
    }
}