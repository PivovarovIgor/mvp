package ru.brauer.mvp.model.githubusers

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.brauer.mvp.presenter.users.IGithubUsersRepo

class GithubUsersRepo(private val api: IGithub) : IGithubUsersRepo {

    override fun getUsers() = api.loadUsers().subscribeOn(Schedulers.io())
}