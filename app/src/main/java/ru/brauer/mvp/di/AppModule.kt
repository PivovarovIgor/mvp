package ru.brauer.mvp.di

import dagger.Module
import dagger.Provides
import ru.brauer.mvp.App
import javax.inject.Singleton

@Module
class AppModule(context: App) {

    private val app = context

    @Singleton
    @Provides
    fun getAppContext(): App = app
}