package ru.brauer.mvp

import android.app.Application
import ru.brauer.mvp.presenter.MainPresenter

class App : Application() {

    val mainPresenter: MainPresenter by lazy { MainPresenter() }
}