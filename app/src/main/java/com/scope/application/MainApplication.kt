package com.scope.application

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.scope.application.di.ApplicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application(){

    init {
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            ApplicationModule.loadModules()
        }
    }


    override fun onCreate() {
        super.onCreate()

        /** persistence library to handle instance of objects */
        Hawk.init(this@MainApplication).build();

    }


}