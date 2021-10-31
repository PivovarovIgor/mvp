package ru.brauer.mvp.presenter.user

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IUserView : MvpView {

    fun init()

    fun setLogin(login: String)

    fun updateListOfRepositories()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun alertMessage(message: String)
}