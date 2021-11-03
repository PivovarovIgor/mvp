package ru.brauer.mvp.model.githubusers

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.brauer.mvp.model.INetworkStatus
import ru.brauer.mvp.model.orm.AppDataBase
import ru.brauer.mvp.model.orm.RoomGithubRepository

class GithubRepositoriesRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val db: AppDataBase
) : IGithubRepositoriesRepo {
    override fun getRepositories(user: GithubUser): Single<List<GithubRepository>> =

        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                user.reposUrl?.let { url ->
                    api.getRepos(url)
                        .flatMap { repositories ->
                            Single.fromCallable {
                                val roomUser = user.login?.let {
                                    db.userDao.findByLogin(it)
                                } ?: throw IllegalStateException("No such user in cache")
                                val roomRepos = repositories.map {
                                    RoomGithubRepository(
                                        it.id ?: "",
                                        it.name ?: "",
                                        it.fullName ?: "",
                                        it.forksCount ?: 0,
                                        it.language ?: "",
                                        it.htmlUrl ?: "",
                                        roomUser.uid
                                    )
                                }
                                db.repositoryDao.insert(roomRepos)
                                repositories
                            }
                        }
                }
                    ?: Single.error<List<GithubRepository>>(IllegalStateException("User has no repos url"))
                        .subscribeOn(Schedulers.io())
            } else {
                Single.fromCallable {
                    val roomUser = user.login?.let {
                        db.userDao.findByLogin(it)
                    } ?: throw IllegalStateException("No such user in cach")
                    db.repositoryDao.findForUser(roomUser.uid).map {
                        GithubRepository(
                            it.uid,
                            it.mame,
                            it.fullName,
                            it.language,
                            it.forksCount,
                            it.htmlUrl
                        )
                    }
                }
            }
        }
            .subscribeOn(Schedulers.io())
}