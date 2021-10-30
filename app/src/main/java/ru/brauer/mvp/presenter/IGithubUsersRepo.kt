package ru.brauer.mvp.presenter

import io.reactivex.rxjava3.core.Single
import ru.brauer.mvp.model.GithubUser

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
}