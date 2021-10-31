package ru.brauer.mvp.model.githubusers

import io.reactivex.rxjava3.core.Single

interface IGithubRepositoriesRepo {
    fun getRepositories(url: String): Single<List<GithubRepository>>
}