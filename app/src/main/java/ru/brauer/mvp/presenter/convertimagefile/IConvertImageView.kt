package ru.brauer.mvp.presenter.convertimagefile

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType


interface IConvertImageView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun loadImageFromFile(bytesOfBmp: ByteArray)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showAlert(message: String)

    @StateStrategyType(AddToEndStrategy::class)
    fun showState(message: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setStateOfButton(state: ImageConversionButtonStates)
}