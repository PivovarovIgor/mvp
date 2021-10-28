package ru.brauer.mvp.presenter

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType


interface IConvertImageView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun loadImageFromFile(bytesOfBmp: ByteArray)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message:String)
}