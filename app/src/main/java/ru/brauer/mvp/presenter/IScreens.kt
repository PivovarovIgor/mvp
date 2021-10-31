package ru.brauer.mvp.presenter

import com.github.terrakok.cicerone.Screen
import ru.brauer.mvp.model.githubusers.GithubRepository
import ru.brauer.mvp.model.githubusers.GithubUser

interface IScreens {
    fun users(): Screen
    fun user(user: GithubUser): Screen
    fun repositories(repository: GithubRepository): Screen
}