package ru.brauer.mvp.di

import dagger.Module
import dagger.Provides
import ru.brauer.mvp.App
import ru.brauer.mvp.model.AndroidNetworkStatus
import ru.brauer.mvp.model.INetworkStatus
import javax.inject.Singleton

@Module
class NetworkStatusModule {

    @Singleton
    @Provides
    fun networkStatus(context: App): INetworkStatus = AndroidNetworkStatus(context)
}