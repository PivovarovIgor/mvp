package ru.brauer.mvp

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*
import ru.brauer.mvp.model.GithubUser
import ru.brauer.mvp.model.GithubUsersRepo
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkLoadingUsers() {

        val githubUsersRepo = GithubUsersRepo()
        githubUsersRepo
            .getUsers2()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .doOnSubscribe {
                githubUsersRepo.loadNextChunkWithUsers()
                println("subscribt")
            }
            .doOnNext(::println)
            .subscribe()

        Thread.sleep(20000)
    }
}