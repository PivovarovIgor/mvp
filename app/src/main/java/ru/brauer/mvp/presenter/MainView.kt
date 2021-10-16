package ru.brauer.mvp.presenter

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.brauer.mvp.model.GithubUsersRepo

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun init()
    fun updateList()
}