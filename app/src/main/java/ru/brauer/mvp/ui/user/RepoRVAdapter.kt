package ru.brauer.mvp.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.brauer.mvp.databinding.ItemRepoBinding
import ru.brauer.mvp.presenter.user.IRepoItemView
import ru.brauer.mvp.presenter.user.IRepoListPresenter

class RepoRVAdapter(
    private val presenter: IRepoListPresenter
) : RecyclerView.Adapter<RepoRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
    }

    override fun getItemCount(): Int = presenter.getCount()

    inner class ViewHolder(private val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root),
        IRepoItemView {

        override var pos: Int = -1

        override fun setName(name: String) {
            binding.repoName.text = name
        }
    }
}