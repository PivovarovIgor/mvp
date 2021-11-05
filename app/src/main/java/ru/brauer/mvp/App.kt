package ru.brauer.mvp

import android.app.Application
import android.util.Log
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import ru.brauer.mvp.di.AppComponent
import ru.brauer.mvp.di.DaggerAppComponent

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = DaggerAppComponent.builder()
            .build()

        RxJavaPlugins.setErrorHandler {
            Log.e("App", it.message ?: "")
        }
    }
}