package ru.brauer.mvp.model

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.brauer.mvp.presenter.IGithub
import ru.brauer.mvp.presenter.IGithubUsersRepo

class GithubUsersRepo(private val api: IGithub) : IGithubUsersRepo {

    override fun getUsers() = api.loadUsers().subscribeOn(Schedulers.io())
}