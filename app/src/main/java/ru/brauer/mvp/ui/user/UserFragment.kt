package ru.brauer.mvp.ui.user

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.brauer.mvp.App
import ru.brauer.mvp.databinding.FragmentUserBinding
import ru.brauer.mvp.model.AndroidNetworkStatus
import ru.brauer.mvp.model.githubusers.ApiHolder
import ru.brauer.mvp.model.githubusers.GithubRepositoriesRepo
import ru.brauer.mvp.model.githubusers.GithubUser
import ru.brauer.mvp.model.orm.AppDataBase
import ru.brauer.mvp.presenter.user.IUserView
import ru.brauer.mvp.presenter.user.UserPresenter
import ru.brauer.mvp.ui.AndroidScreens
import ru.brauer.mvp.ui.IBackButtonListener

class UserFragment : MvpAppCompatFragment(), IUserView, IBackButtonListener {

    private val presenter by moxyPresenter {
        UserPresenter(
            requireNotNull(arguments?.getParcelable(KEY_ARG_USER)),
            AndroidSchedulers.mainThread(),
            GithubRepositoriesRepo(
                ApiHolder.api,
                AndroidNetworkStatus(requireContext()),
                AppDataBase.getDatabase(requireContext())
            ),
            App.instance.router,
            AndroidScreens()
        )
    }

    private var binding: FragmentUserBinding? = null

    private val adapter: RepoRVAdapter by lazy {
        RepoRVAdapter(presenter.repoListPresenter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentUserBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun init() {
        binding?.run {
            repoList.layoutManager = LinearLayoutManager(context)
            repoList.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun setLogin(login: String) {
        binding?.run {
            userLogin.text = login
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateListOfRepositories() {
        adapter.notifyDataSetChanged()
    }

    override fun alertMessage(message: String) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .create()
            .show()
    }

    override fun backPressed(): Boolean = presenter.backPressed()

    companion object {

        private const val KEY_ARG_USER = "KEY_ARG_USER"

        fun newInstance(user: GithubUser) = UserFragment().apply {
            Bundle().apply {
                putParcelable(KEY_ARG_USER, user)
            }.also {
                arguments = it
            }
        }
    }
}