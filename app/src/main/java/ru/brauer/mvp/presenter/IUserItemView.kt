package ru.brauer.mvp.presenter

interface IUserItemView: IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}