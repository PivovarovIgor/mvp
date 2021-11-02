package ru.brauer.mvp

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import ru.brauer.mvp.model.orm.AppDataBase

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    val navigationHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router

    val dataBase: AppDataBase by lazy {
        Room.databaseBuilder(
            this,
            AppDataBase::class.java,
            "database-file-name.db"
        ).build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        RxJavaPlugins.setErrorHandler {
            Toast.makeText(
                applicationContext,
                "${it.message}",
                Toast.LENGTH_LONG
            ).show()
            Log.e("App", it.message ?: "")
        }
    }
}