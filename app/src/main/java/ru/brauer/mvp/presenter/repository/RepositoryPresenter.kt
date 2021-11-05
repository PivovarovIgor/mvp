package ru.brauer.mvp.presenter.repository

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.brauer.mvp.model.githubusers.GithubRepository
import javax.inject.Inject

class RepositoryPresenter(
    private val githubRepository: GithubRepository
) : MvpPresenter<IRepositoryView>() {

    @Inject lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init(githubRepository)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}