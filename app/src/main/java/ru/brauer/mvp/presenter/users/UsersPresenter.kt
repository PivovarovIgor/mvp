package ru.brauer.mvp.presenter.users

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import ru.brauer.mvp.model.githubusers.GithubUser
import ru.brauer.mvp.presenter.IScreens
import javax.inject.Inject

class UsersPresenter : MvpPresenter<IUsersView>() {

    @Inject
    lateinit var usersRepo: IGithubUsersRepo

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var uiScheduler: Scheduler

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((IUserItemView) -> Unit)? = null

        override fun getCount(): Int = users.size

        override fun bindView(view: IUserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login ?: "")
            view.loadAvatar(user.avatarUrl ?: "")
        }
    }

    private val repositoryObserver = object : SingleObserver<List<GithubUser>> {

        private var disposable: Disposable? = null

        override fun onSubscribe(d: Disposable) {
            disposable?.let {
                if (!it.isDisposed) {
                    it.dispose()
                }
            }
            disposable = d
        }

        override fun onSuccess(users: List<GithubUser>) {
            usersListPresenter.users.addAll(users)
            viewState.updateList()
        }

        override fun onError(e: Throwable) {
            viewState.showMessageError(e.message ?: "Unknown error")
        }
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { itemView ->
            screens.user(usersListPresenter.users[itemView.pos])
                .also { router.navigateTo(it) }
        }
    }

    fun loadData() {
        usersListPresenter.users.clear()
        viewState.updateList()
        usersRepo.getUsers()
            .observeOn(uiScheduler)
            .subscribe(repositoryObserver)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}