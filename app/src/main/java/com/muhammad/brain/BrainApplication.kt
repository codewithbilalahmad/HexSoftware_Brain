package com.muhammad.brain

import android.app.Application
import com.muhammad.brain.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BrainApplication : Application(){
    companion object{
        lateinit var INSTANCE : BrainApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidContext(this@BrainApplication)
            androidLogger()
            modules(appModule)
        }
    }
}