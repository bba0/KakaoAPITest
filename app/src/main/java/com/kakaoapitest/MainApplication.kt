package com.kakaoapitest

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class MainApplication : Application() {
    companion object {

    }
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}