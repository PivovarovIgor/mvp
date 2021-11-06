package ru.brauer.mvp.di

import dagger.Module
import dagger.Provides
import ru.brauer.mvp.model.INetworkStatus
import ru.brauer.mvp.model.githubusers.GithubRepositoriesRepo
import ru.brauer.mvp.model.githubusers.GithubUsersRepo
import ru.brauer.mvp.model.githubusers.IDataSource
import ru.brauer.mvp.model.githubusers.IGithubRepositoriesRepo
import ru.brauer.mvp.model.room.IRoomGithubRepositoryCache
import ru.brauer.mvp.model.room.IRoomGithubUsersCache
import ru.brauer.mvp.presenter.users.IGithubUsersRepo
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun githubUsersRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IRoomGithubUsersCache
    ): IGithubUsersRepo = GithubUsersRepo(api, networkStatus, cache)

    @Singleton
    @Provides
    fun githubRepositoriesRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IRoomGithubRepositoryCache
    ): IGithubRepositoriesRepo = GithubRepositoriesRepo(api, networkStatus, cache)
}