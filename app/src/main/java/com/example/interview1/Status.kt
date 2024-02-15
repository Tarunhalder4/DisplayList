package com.example.interview1

class Data<T>(val status:Status? = null,
              val data:T? = null,
              val error:String? = null)

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}