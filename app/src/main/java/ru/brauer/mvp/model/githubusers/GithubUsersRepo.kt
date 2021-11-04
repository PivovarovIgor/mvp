package ru.brauer.mvp.model.githubusers

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.brauer.mvp.model.INetworkStatus
import ru.brauer.mvp.model.room.IRoomGithubUsersCache
import ru.brauer.mvp.presenter.users.IGithubUsersRepo

class GithubUsersRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val cache: IRoomGithubUsersCache
) : IGithubUsersRepo {

    override fun getUsers(): Single<List<GithubUser>> =
        networkStatus.isOnlineSingle()
            .flatMap { isOnline ->
                if (isOnline) {
                    api.getUsers()
                        .flatMap { users ->
                            Single.fromCallable {
                                cache.save(users)
                                users
                            }
                        }
                } else {
                    Single.fromCallable { cache.get() }
                }
                    .subscribeOn(Schedulers.io())
            }
}

