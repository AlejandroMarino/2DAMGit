package com.example.compose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoomApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}