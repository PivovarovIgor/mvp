package ru.brauer.mvp.presenter.repository

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.brauer.mvp.model.githubusers.GithubRepository

class RepositoryPresenter(
    private val githubRepository: GithubRepository,
    private val router: Router
) : MvpPresenter<IRepositoryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init(githubRepository)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}