package ru.brauer.mvp.model.room

import ru.brauer.mvp.model.githubusers.GithubRepository
import ru.brauer.mvp.model.githubusers.GithubUser

interface IRoomGithubRepositoryCache {
    fun save(user: GithubUser, repositories: List<GithubRepository>)
    fun get(user: GithubUser): List<GithubRepository>
}