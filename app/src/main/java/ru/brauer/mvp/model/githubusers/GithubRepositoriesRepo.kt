package ru.brauer.mvp.model.githubusers

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubRepositoriesRepo(private val api: IGithub) : IGithubRepositoriesRepo {
    override fun getRepositories(url: String): Single<List<GithubRepository>> =
        api.getRepos(url).subscribeOn(Schedulers.io())
}