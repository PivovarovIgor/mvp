package ru.brauer.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.brauer.mvp.model.GithubUser

class UserPresenter(private val user: GithubUser, private val router: Router) : MvpPresenter<IUserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setLogin(user.login ?: "")
    }

    fun backPressed(): Boolean {
        router.backTo(null)
        return true
    }
}