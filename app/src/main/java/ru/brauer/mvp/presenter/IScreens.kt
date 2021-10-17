package ru.brauer.mvp.presenter

import com.github.terrakok.cicerone.Screen
import ru.brauer.mvp.model.GithubUser

interface IScreens {
    fun users(): Screen
    fun user(user: GithubUser): Screen
}