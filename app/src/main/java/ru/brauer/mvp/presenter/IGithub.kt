package ru.brauer.mvp.presenter

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import ru.brauer.mvp.model.GithubUser

interface IGithub {
    @GET("users")
    fun loadUsers(): Single<List<GithubUser>>
}