package ru.brauer.mvp

import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.brauer.mvp.model.githubusers.ApiHolder
import ru.brauer.mvp.model.githubusers.GithubUsersRepo

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

        val githubUsersRepo = GithubUsersRepo(ApiHolder.api)
        githubUsersRepo
            .getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .doOnSubscribe {
                println("subscribt")
            }
            .subscribe(::println)

        Thread.sleep(3000)
    }
}