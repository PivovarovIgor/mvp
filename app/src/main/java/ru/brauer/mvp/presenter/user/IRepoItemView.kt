package ru.brauer.mvp.presenter.user

import ru.brauer.mvp.model.githubusers.GithubRepository
import ru.brauer.mvp.presenter.IItemView

interface IRepoItemView : IItemView {
    fun setData(name: GithubRepository)
}