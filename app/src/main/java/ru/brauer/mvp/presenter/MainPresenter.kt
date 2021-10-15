package ru.brauer.mvp.presenter

import moxy.MvpPresenter
import ru.brauer.mvp.model.CountersModel

class MainPresenter(private val model: CountersModel = CountersModel()) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun counterClick(id: CounterId) {
        val nextValueText = model.next(id.index).toString()
        with(viewState) {
            when (id) {
                CounterId.FIRST -> setButton1Text(nextValueText)
                CounterId.SECOND -> setButton2Text(nextValueText)
                CounterId.THIRD -> setButton3Text(nextValueText)
            }
        }
    }
}