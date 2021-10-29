package ru.brauer.mvp.model

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class GithubUsersRepo {

    private val repositories = (1..100)
        .shuffled()
        .map { GithubUser("login $it") }

    private val bs = BehaviorSubject.create<List<GithubUser>>()

    fun getUsers2(): Observable<List<GithubUser>> = client.loadUsers().toObservable()

    fun getUsers() = client.loadUsers()

    val client = RetrofitHolder().api
}