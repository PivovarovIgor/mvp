package ru.brauer.mvp.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.brauer.mvp.presenter.IScreens

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
}