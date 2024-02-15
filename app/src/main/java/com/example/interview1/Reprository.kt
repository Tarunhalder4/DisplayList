package com.example.interview1

import com.example.interview1.model.SongsModel
import retrofit2.Call
import javax.inject.Inject

class Reprository @Inject constructor(private val apiService: ApiService) {

    fun getAllSongs(): Call<SongsModel> {
        return apiService.getAllSongs()
    }
}