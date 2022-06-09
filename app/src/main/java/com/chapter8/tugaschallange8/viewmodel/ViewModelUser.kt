package com.chapter8.tugaschallange8.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapter8.tugaschallange8.model.DataUserResponseItem
import com.chapter8.tugaschallange8.model.PostUser
import com.chapter8.tugaschallange8.model.RequestUser
import com.chapter8.tugaschallange8.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ViewModelUser @Inject constructor(@Named("ApiServiceUser") apiService: ApiService) : ViewModel(){
    private val userState = MutableStateFlow(emptyList<DataUserResponseItem>())
    val dataUserState : StateFlow<List<DataUserResponseItem>> get() = userState
    private val api = apiService

    init {
        viewModelScope.launch {
            val dataUser = apiService.getAllUser()
            userState.value = dataUser
        }
    }

    fun insertNewUSer(requestUser: RequestUser): Boolean{
        var messageResponse = false
        api.addNewUser(requestUser)
            .enqueue(object : Callback<PostUser> {
                override fun onResponse(call: Call<PostUser>, response: Response<PostUser>) {
                    messageResponse = response.isSuccessful
                }

                override fun onFailure(call: Call<PostUser>, t: Throwable) {
                    messageResponse = false
                }

            })
        return messageResponse
    }
}