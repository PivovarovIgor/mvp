package ru.brauer.mvp.presenter

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IUsersView : MvpView {

    fun init()

    fun updateList()

    fun notifyItemInserted(position: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessageError(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessageOnComplete()
}