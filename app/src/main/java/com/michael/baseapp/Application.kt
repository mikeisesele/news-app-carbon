package com.michael.baseapp

import android.app.Application
import com.michael.easylog.DefaultLogger
import com.michael.easylog.EasyLog
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        EasyLog.setUp {
            debugMode(true)
            addDefaultLogger((DefaultLogger.DEFAULT_ANDROID))
            filterTag("NAC-LOG")
        }
    }
}
