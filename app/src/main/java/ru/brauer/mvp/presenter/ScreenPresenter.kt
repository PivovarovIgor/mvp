package ru.brauer.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class ScreenPresenter : MvpPresenter<IMainView>() {

    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.users())
    }

    fun backClicked() {
        router.exit()
    }
}