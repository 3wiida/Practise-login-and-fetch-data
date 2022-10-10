package com.example.testallthings.di

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    lateinit var c: Context

    fun getContext(): Context? {
        return c
    }

    override fun onCreate() {
        c=applicationContext
        super.onCreate()

    }

}