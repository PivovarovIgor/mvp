package ru.brauer.mvp.model.room

import ru.brauer.mvp.model.githubusers.GithubUser

interface IRoomGithubUsersCache {
    fun save(users: List<GithubUser>)
    fun get(): List<GithubUser>
}