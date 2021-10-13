package ru.brauer.mvp.presenter

import ru.brauer.mvp.model.CountersModel

class MainPresenter(private val model: CountersModel = CountersModel()) {

    private var view: MainView? = null

    fun onAttachView(v: MainView) {
        view = v
        CounterId.values().forEach(::showDataOnView)
    }

    fun onDetachView() {
        view = null
    }

    fun counterClick(id: CounterId) {
        view?.run {
            val nextValueText = model.next(id.index).toString()
            showDataOnView(id, nextValueText)
        }
    }

    private fun showDataOnView(
        id: CounterId,
        nextValueText: String = model.getCurrent(id.index).toString()
    ) {
        view?.run {
            when (id) {
                CounterId.FIRST -> setButton1Text(nextValueText)
                CounterId.SECOND -> setButton2Text(nextValueText)
                CounterId.THIRD -> setButton3Text(nextValueText)
            }
        }
    }
}