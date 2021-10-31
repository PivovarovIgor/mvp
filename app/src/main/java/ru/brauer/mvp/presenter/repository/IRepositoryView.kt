package ru.brauer.mvp.presenter.repository

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.brauer.mvp.model.githubusers.GithubRepository

@StateStrategyType(AddToEndSingleStrategy::class)
interface IRepositoryView : MvpView {
    fun init(githubRepository: GithubRepository)
}

