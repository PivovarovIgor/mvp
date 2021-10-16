package ru.brauer.mvp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.brauer.mvp.databinding.ActivityMainBinding
import ru.brauer.mvp.model.GithubUsersRepo
import ru.brauer.mvp.presenter.MainView
import ru.brauer.mvp.presenter.ScreenPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private val presenter: ScreenPresenter by moxyPresenter { ScreenPresenter(GithubUsersRepo()) }
    private val adapter: UsersRVAdapter by lazy { UsersRVAdapter(presenter.usersListPresenter) }

    private val vb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    override fun init() {
        vb.rvUsers.layoutManager = LinearLayoutManager(this)
        vb.rvUsers.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateList() {
        adapter.notifyDataSetChanged()
    }
}