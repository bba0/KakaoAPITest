package com.kakaoapitest

import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco

class MainApplication : MultiDexApplication() {
    companion object {

    }
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}