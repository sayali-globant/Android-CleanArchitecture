package com.example.avengers

import android.app.Application
import com.example.avengers.di.AppModule
import com.example.avengers.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AvengersApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AvengersApplication)
            modules(listOf(AppModule, NetworkModule))

        }
    }
}