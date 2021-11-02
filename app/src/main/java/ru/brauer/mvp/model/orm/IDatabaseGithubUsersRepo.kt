package ru.brauer.mvp.model.orm

import io.reactivex.rxjava3.core.Single
import ru.brauer.mvp.model.githubusers.GithubUser

interface IDatabaseGithubUsersRepo {

    fun getUsers(): Single<List<GithubUser>>

    fun saveAllUsers(users: List<GithubUser>)
}