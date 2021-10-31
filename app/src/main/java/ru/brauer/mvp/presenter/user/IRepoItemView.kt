package ru.brauer.mvp.presenter.user

import ru.brauer.mvp.presenter.IItemView

interface IRepoItemView : IItemView {
    fun setName(name: String)
}