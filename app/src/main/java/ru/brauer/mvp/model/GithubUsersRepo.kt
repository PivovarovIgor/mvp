package ru.brauer.mvp.model

import io.reactivex.rxjava3.core.Observable
import java.io.IOException
import kotlin.random.Random

class GithubUsersRepo {

    private val repositories = (1..100).map { GithubUser("login $it") }

    fun getUsers(): Observable<GithubUser> = Observable.create { emitter ->
        val random = Random(100)
        try {
            repositories.forEach {
                if (random.nextInt(100) == 1) throw IOException("IO error")
                emitter.onNext(it)
            }
        } catch (ex: Throwable) {
            emitter.onError(ex)
        }
        emitter.onComplete()
    }
}