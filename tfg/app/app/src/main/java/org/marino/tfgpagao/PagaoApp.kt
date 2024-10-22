package org.marino.tfgpagao

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PagaoApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}