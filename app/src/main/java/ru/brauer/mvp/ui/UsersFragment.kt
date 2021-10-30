package ru.brauer.mvp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.brauer.mvp.App
import ru.brauer.mvp.R
import ru.brauer.mvp.databinding.FragmentUsersBinding
import ru.brauer.mvp.model.ApiHolder
import ru.brauer.mvp.model.GithubUsersRepo
import ru.brauer.mvp.presenter.AndroidScreens
import ru.brauer.mvp.presenter.IBackButtonListener
import ru.brauer.mvp.presenter.IUsersView
import ru.brauer.mvp.presenter.UsersPresenter

class UsersFragment : MvpAppCompatFragment(), IUsersView, IBackButtonListener {
    companion object {
        fun newInstance() = UsersFragment()
    }

    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            AndroidSchedulers.mainThread(),
            GithubUsersRepo(ApiHolder.api),
            App.instance.router,
            AndroidScreens()
        )
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
        view?.let {
            Snackbar
                .make(it, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.reload) {
                    presenter.loadData()
                }
                .show()
        }
    }

    override fun showMessageOnComplete() {
        Toast.makeText(context, "on complete", Toast.LENGTH_LONG).show()
    }

    override fun backPressed(): Boolean = presenter.backPressed()
}