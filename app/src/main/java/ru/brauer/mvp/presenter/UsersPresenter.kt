package ru.brauer.mvp.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.brauer.mvp.model.GithubUser
import ru.brauer.mvp.model.GithubUsersRepo

class UsersPresenter(
    private val usersRepo: GithubUsersRepo,
    private val router: Router,
    private val screens: IScreens
) :
    MvpPresenter<IUsersView>() {

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((IUserItemView) -> Unit)? = null

        override fun getCount(): Int = users.size

        override fun bindView(view: IUserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
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

        override fun onSuccess(t: List<GithubUser>) {
            usersListPresenter.users.addAll(t)
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(repositoryObserver)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}