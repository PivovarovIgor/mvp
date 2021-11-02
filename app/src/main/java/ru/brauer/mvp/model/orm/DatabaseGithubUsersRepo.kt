package ru.brauer.mvp.model.orm

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.brauer.mvp.model.githubusers.GithubUser

class DatabaseGithubUsersRepo(private val database: AppDataBase) : IDatabaseGithubUsersRepo {

    override fun getUsers(): Single<List<GithubUser>> =
        Single.create<List<GithubUser>> { emitter ->
            database.userDao()
                .getAll()
                .map { GithubUser(it.uid.toString(), it.login, it.avatarUrl, null) }
                .also { emitter.onSuccess(it) }
        }
            .subscribeOn(Schedulers.io())


    override fun saveAllUsers(users: List<GithubUser>): Unit =
        users.mapNotNull {
            it.id?.toIntOrNull()?.let { id ->
                User(id, it.login, it.avatarUrl)
            }
        }.let { usersAdo ->
            Completable.create { emitter ->
                database.userDao().addAll(usersAdo)
                emitter.onComplete()
            }.subscribeOn(Schedulers.io())
                .subscribe()
        }
}