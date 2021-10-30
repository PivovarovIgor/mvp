package ru.brauer.mvp.presenter.users

import ru.brauer.mvp.presenter.IItemView

interface IUserItemView: IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}