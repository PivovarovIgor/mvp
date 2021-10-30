package ru.brauer.mvp.model.githubusers

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IGithub {
    @GET("users")
    fun loadUsers(): Single<List<GithubUser>>
}