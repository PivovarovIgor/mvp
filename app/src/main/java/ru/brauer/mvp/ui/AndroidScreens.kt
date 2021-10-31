package ru.brauer.mvp.ui

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.brauer.mvp.model.githubusers.GithubRepository
import ru.brauer.mvp.model.githubusers.GithubUser
import ru.brauer.mvp.presenter.IScreens
import ru.brauer.mvp.ui.repository.GithubRepositoryFragment
import ru.brauer.mvp.ui.user.UserFragment
import ru.brauer.mvp.ui.users.UsersFragment

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun user(user: GithubUser) = FragmentScreen { UserFragment.newInstance(user) }
    override fun repositories(repository: GithubRepository): Screen = FragmentScreen {
        GithubRepositoryFragment.newInstance(repository)
    }
}