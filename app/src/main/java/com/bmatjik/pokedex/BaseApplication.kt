package com.bmatjik.pokedex

import android.app.Application
import com.bmatjik.pokedex.common.isApplicationDebuggable
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (isApplicationDebuggable()){
            Timber.plant(Timber.DebugTree())
        }
    }
}