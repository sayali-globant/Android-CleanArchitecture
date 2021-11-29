package com.example.marvels

import android.app.Application
import com.example.marvels.di.AppModule
import com.example.marvels.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MarvelsApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MarvelsApplication)
            modules(listOf(AppModule, NetworkModule))

        }
    }
}