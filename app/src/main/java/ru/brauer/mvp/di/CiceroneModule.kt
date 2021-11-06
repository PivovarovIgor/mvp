package ru.brauer.mvp.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import ru.brauer.mvp.presenter.IScreens
import ru.brauer.mvp.ui.AndroidScreens
import javax.inject.Singleton

@Module
class CiceroneModule {

    private val cicerone = Cicerone.create()

    @Provides
    fun cicerone(): Cicerone<Router> = cicerone

    @Singleton
    @Provides
    fun navigationHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

    @Singleton
    @Provides
    fun router(): Router = cicerone.router

    @Singleton
    @Provides
    fun screens(): IScreens = AndroidScreens()
}