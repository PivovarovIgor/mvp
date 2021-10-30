package ru.brauer.mvp.presenter.users

import io.reactivex.rxjava3.core.Single
import ru.brauer.mvp.model.githubusers.GithubUser

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
}