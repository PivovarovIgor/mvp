package ru.brauer.mvp.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.brauer.mvp.App
import ru.brauer.mvp.databinding.FragmentUserBinding
import ru.brauer.mvp.model.githubusers.GithubUser
import ru.brauer.mvp.ui.IBackButtonListener
import ru.brauer.mvp.presenter.user.IUserView
import ru.brauer.mvp.presenter.user.UserPresenter

class UserFragment() : MvpAppCompatFragment(), IUserView, IBackButtonListener {

    private val presenter by moxyPresenter {
        UserPresenter(requireNotNull(arguments?.getParcelable(KEY_ARG_USER)), App.instance.router)
    }

    private var vb: FragmentUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentUserBinding.inflate(inflater, container, false).also {
            vb = it
        }.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun setLogin(login: String) {
        vb?.run {
            userLogin.text = login
        }
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