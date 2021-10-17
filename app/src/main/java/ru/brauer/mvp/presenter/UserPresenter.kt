package ru.brauer.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.brauer.mvp.model.GithubUser

class UserPresenter(val user: GithubUser, val router: Router) : MvpPresenter<IUserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun backPressed(): Boolean {
        router.backTo(null)
        return true
    }
}