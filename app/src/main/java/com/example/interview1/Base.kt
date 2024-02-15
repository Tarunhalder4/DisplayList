package com.example.interview1

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Base : Application() {

    override fun onCreate() {
        super.onCreate()

    }

}