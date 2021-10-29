package ru.brauer.mvp.model

import com.google.gson.JsonParser
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import okhttp3.*
import java.io.IOException

class GithubUsersRepo {

    private val repositories = (1..100)
        .shuffled()
        .map { GithubUser("login $it") }

    private val bs = BehaviorSubject.create<List<GithubUser>>()

    fun getUsers2(): Observable<List<GithubUser>> = bs

    fun loadNextChunkWithUsers() {
        client
            .newCall(Request.Builder().url("https://api.github.com/users").build())
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("fail")
                }

                override fun onResponse(call: Call, response: Response) {
                    val s = response.body?.string()
                    val list = JsonParser.parseString(s).asJsonArray.toList()
                        .map { it.asJsonObject }
                        .map { it["login"] }
                        .map { GithubUser(it.asString) }
                    bs.onNext(list)
                }
            })
    }

    fun getUsers() {


    }

    val client = OkHttpClient.Builder().build()
}