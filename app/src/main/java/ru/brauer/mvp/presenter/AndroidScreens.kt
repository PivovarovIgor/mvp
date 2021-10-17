package ru.brauer.mvp.presenter

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.brauer.mvp.model.GithubUser
import ru.brauer.mvp.ui.UserFragment
import ru.brauer.mvp.ui.UsersFragment

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun user(user: GithubUser) = FragmentScreen { UserFragment.newInstance(user) }
}