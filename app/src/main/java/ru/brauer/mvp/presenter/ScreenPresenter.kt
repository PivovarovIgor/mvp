package ru.brauer.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class ScreenPresenter(private val router: Router, private val screens: IScreens) :
    MvpPresenter<IMainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.users())
    }

    fun backClicked() {
        router.exit()
    }
}