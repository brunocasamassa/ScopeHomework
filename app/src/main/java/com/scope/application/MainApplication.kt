package com.scope.application

import android.app.Application

class MainApplication : Application(){


    init {
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
        }
    }



    override fun onCreate() {
        super.onCreate()

        //persistence library to handle instance of objects
        Hawk.init(this@MainApplication).build();

    }


}