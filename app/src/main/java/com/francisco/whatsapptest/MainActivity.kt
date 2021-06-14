package com.francisco.whatsapptest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as WhatsApp).getAppComponent()
    }

    companion object {
        var sInstance: MainActivity? = null
        fun getInstance(): MainActivity {
            if (sInstance == null) {
                synchronized(MainActivity::class) {
                    if (sInstance == null) {
                        sInstance = MainActivity()
                    }
                }
            }
            return sInstance!!
        }
    }
}