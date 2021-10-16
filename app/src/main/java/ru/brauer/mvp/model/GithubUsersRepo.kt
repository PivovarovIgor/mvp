package ru.brauer.mvp.model

class GithubUsersRepo {

    private val repositories = (1..10).map { GithubUser("login $it") }

    fun getUsers(): List<GithubUser> = repositories
}