package ru.brauer.mvp.ui.users

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.brauer.mvp.App
import ru.brauer.mvp.R
import ru.brauer.mvp.databinding.FragmentUsersBinding
import ru.brauer.mvp.model.AndroidNetworkStatus
import ru.brauer.mvp.model.githubusers.ApiHolder
import ru.brauer.mvp.model.githubusers.GithubUsersRepo
import ru.brauer.mvp.model.room.AppDataBase
import ru.brauer.mvp.model.room.RoomGithubUsersCache
import ru.brauer.mvp.presenter.users.IUsersView
import ru.brauer.mvp.presenter.users.UsersPresenter
import ru.brauer.mvp.ui.GlideImageLoader
import ru.brauer.mvp.ui.IBackButtonListener

class UsersFragment : MvpAppCompatFragment(), IUsersView, IBackButtonListener {
    companion object {
        fun newInstance() = UsersFragment()
    }

    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            AndroidSchedulers.mainThread(),
            GithubUsersRepo(
                ApiHolder.api,
                AndroidNetworkStatus(requireContext()),
                RoomGithubUsersCache(AppDataBase.getDatabase(requireContext()))
            )
        ).apply { App.instance.appComponent.inject(this) }
    }
    private val adapter: UsersRVAdapter by lazy {
        UsersRVAdapter(presenter.usersListPresenter, GlideImageLoader())
    }

    private var vb: FragmentUsersBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUsersBinding.inflate(inflater, container, false)
        .also { vb = it }
        .root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
        vb?.apply {
            rvUsers.layoutManager = LinearLayoutManager(context)
            rvUsers.adapter = adapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun notifyItemInserted(position: Int) {
        adapter.notifyItemInserted(position)
    }

    override fun showMessageError(message: String) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(R.string.reload) { _, _ ->
                presenter.loadData()
            }
            .create()
            .show()
    }

    override fun showMessageOnComplete() {
        Toast.makeText(context, "on complete", Toast.LENGTH_LONG).show()
    }

    override fun backPressed(): Boolean = presenter.backPressed()
}