package com.example.interview1

import com.example.interview1.model.Result
import com.example.interview1.model.SongsModel
import retrofit2.Call
import retrofit2.http.GET


interface ApiService {

    @GET("3u3sDEM")
    fun getAllSongs():Call<SongsModel>

}