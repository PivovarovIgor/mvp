package ru.brauer.mvp.model

import io.reactivex.rxjava3.core.Observable
import java.io.IOException
import kotlin.random.Random

class GithubUsersRepo {

    private val repositories = (1..100)
        .shuffled()
        .map { GithubUser("login $it") }

    fun getUsers(): Observable<GithubUser> = Observable.create { emitter ->
        try {
            repositories.forEach {
                if (Random.nextInt(1000) == 1) throw IOException("IO error")
                emitter.onNext(it)
                Thread.sleep(Random.nextLong(100))
            }
            emitter.onComplete()
        } catch (ex: Throwable) {
            emitter.onError(ex)
        }
    }
}