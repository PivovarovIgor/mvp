package ru.brauer.mvp.presenter

import android.widget.Toast
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
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

    private val repositoryObserver = object : Observer<GithubUser> {

        private var disposable: Disposable? = null

        override fun onSubscribe(disposable: Disposable) {
            this.disposable?.apply {
                if (!isDisposed) {
                    dispose()
                }
            }
            usersListPresenter.users.clear()
            this.disposable = disposable
        }

        override fun onNext(user: GithubUser) {
            usersListPresenter.users.indexOfFirst { it.login > user.login }
                .let { if (it == -1) usersListPresenter.users.size else it }
                .let {
                    usersListPresenter.users.add(it, user)
                    viewState.notifyItemInserted(it)
                }
        }

        override fun onError(e: Throwable) {
            viewState.showMessageError(e.message ?: "Undefine error")
        }

        override fun onComplete() {
            viewState.showMessageOnComplete()
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
//        usersListPresenter.users.clear()
//        viewState.updateList()
//        usersRepo.getUsers()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(repositoryObserver)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}