package com.example.interview1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import com.example.interview1.model.Result
import com.example.interview1.model.SongsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@HiltViewModel
class ViewModel @Inject constructor(private val repository: Reprository):ViewModel() {


    private var _songsStatus = MutableLiveData<Data<SongsModel>>()
    val songsStatus: LiveData<Data<SongsModel>>
        get() = _songsStatus


    fun getSongsStatus(){
        _songsStatus.postValue(Data(Status.LOADING))
        viewModelScope.launch {
            repository.getAllSongs().enqueue(object : Callback<SongsModel> {
                override fun onResponse(call: Call<SongsModel>, response: Response<SongsModel>) {
                    _songsStatus.postValue(Data(Status.SUCCESS, response.body()))
                }

                override fun onFailure(call: Call<SongsModel>, t: Throwable) {
                    _songsStatus.postValue(Data(Status.ERROR, error = t.message))
                }
            })
        }
    }


}