package com.example.compose.framework.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoomApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}