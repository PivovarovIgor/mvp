package ru.brauer.mvp.ui.repository

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.brauer.mvp.App
import ru.brauer.mvp.databinding.FragmentGithubRepositoryBinding
import ru.brauer.mvp.model.githubusers.GithubRepository
import ru.brauer.mvp.presenter.repository.IRepositoryView
import ru.brauer.mvp.presenter.repository.RepositoryPresenter
import ru.brauer.mvp.ui.IBackButtonListener

class GithubRepositoryFragment : MvpAppCompatFragment(), IRepositoryView, IBackButtonListener {

    companion object {

        private const val KEY_DATA = "GithubRepositoryFragment_KEY_DATA"

        fun newInstance(githubRepository: GithubRepository): GithubRepositoryFragment =
            GithubRepositoryFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_DATA, githubRepository)
                }
            }
    }

    private val presenter: RepositoryPresenter by moxyPresenter {
        RepositoryPresenter(
            requireNotNull(arguments?.getParcelable(KEY_DATA)),
            App.instance.router
        )
    }

    private var binding: FragmentGithubRepositoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentGithubRepositoryBinding.inflate(inflater, container, false)
        .also {
            binding = it
        }
        .root

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun init(githubRepository: GithubRepository) {
        binding?.run {
            valueName.text = githubRepository.name
            valueFullName.text = githubRepository.fullName
            valueForksCount.text = githubRepository.forksCount.toString()
            valueLanguage.text = githubRepository.language
            valueHtmlUrl.text = githubRepository.htmlUrl
            valueHtmlUrl.paintFlags = valueHtmlUrl.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            valueHtmlUrl.setOnClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse(githubRepository.htmlUrl)).run {
                    startActivity(this)
                }
            }
        }
    }

    override fun backPressed(): Boolean = presenter.backPressed()
}