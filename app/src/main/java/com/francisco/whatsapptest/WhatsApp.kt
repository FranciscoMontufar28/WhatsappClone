package com.francisco.whatsapptest

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.francisco.whatsapptest.di.AppComponent
import com.francisco.whatsapptest.di.DaggerAppComponent

class WhatsApp : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        appComponent = initAppComponent()
    }

    private fun initAppComponent() = DaggerAppComponent.factory().create(this)

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}