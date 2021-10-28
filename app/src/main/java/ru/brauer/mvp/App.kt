package ru.brauer.mvp

import android.app.Application
import android.widget.Toast
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.plugins.RxJavaPlugins

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    val navigationHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router

    override fun onCreate() {
        super.onCreate()
        instance = this
        RxJavaPlugins.setErrorHandler {
            if (it is UndeliverableException) {
                Toast.makeText(
                    applicationContext,
                    "UndeliverableException: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}