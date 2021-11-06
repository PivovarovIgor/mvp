package ru.brauer.mvp.di

import dagger.Module
import dagger.Provides
import ru.brauer.mvp.model.room.*
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun githubRoomUsersCache(db: AppDataBase): IRoomGithubUsersCache = RoomGithubUsersCache(db)

    @Singleton
    @Provides
    fun githubRoomRepositoryCache(db: AppDataBase): IRoomGithubRepositoryCache =
        RoomGithubRepositoryCache(db)
}