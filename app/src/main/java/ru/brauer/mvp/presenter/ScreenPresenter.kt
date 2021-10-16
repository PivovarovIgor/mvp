package ru.brauer.mvp.presenter

import moxy.MvpPresenter
import ru.brauer.mvp.model.GithubUser
import ru.brauer.mvp.model.GithubUsersRepo
import ru.brauer.mvp.ui.IUserListPresenter
import ru.brauer.mvp.ui.UserItemView

class ScreenPresenter(private val usersRepo: GithubUsersRepo) : MvpPresenter<MainView>() {

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount(): Int = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { itemView ->
            //TODO переход на экран пользователя
        }
    }

    private fun loadData() {
        val users = usersRepo.getUsers()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }
}