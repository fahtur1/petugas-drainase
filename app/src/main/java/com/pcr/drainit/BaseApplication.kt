package com.pcr.drainit

import android.app.Application
import com.pcr.drainit.utill.Session
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Session.init(this)
    }

}