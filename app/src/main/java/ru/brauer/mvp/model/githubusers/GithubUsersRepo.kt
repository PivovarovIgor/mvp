package ru.brauer.mvp.model.githubusers

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.brauer.mvp.model.INetworkStatus
import ru.brauer.mvp.model.orm.AppDataBase
import ru.brauer.mvp.model.orm.RoomGithubUser
import ru.brauer.mvp.presenter.users.IGithubUsersRepo

class GithubUsersRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val db: AppDataBase
) : IGithubUsersRepo {

    override fun getUsers(): Single<List<GithubUser>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                api.getUsers()
                    .flatMap { users ->
                        Single.fromCallable {
                            val roomUsers = users.map { user ->
                                RoomGithubUser(
                                    user.id ?: "",
                                    user.login ?: "",
                                    user.avatarUrl ?: "",
                                    user.reposUrl ?: ""
                                )
                            }
                            db.userDao.insert(roomUsers)
                            users
                        }
                    }
            } else {
                Single.fromCallable {
                    db.userDao.getAll().map { roomUser ->
                        GithubUser(
                            roomUser.uid,
                            roomUser.login,
                            roomUser.avatarUrl,
                            roomUser.reposUrl
                        )
                    }
                }
            }
                .subscribeOn(Schedulers.io())
        }
}