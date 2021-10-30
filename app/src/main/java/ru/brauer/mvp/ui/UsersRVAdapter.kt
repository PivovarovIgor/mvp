package ru.brauer.mvp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.brauer.mvp.databinding.ItemUserBinding
import ru.brauer.mvp.model.IImageLoader
import ru.brauer.mvp.presenter.IUserItemView
import ru.brauer.mvp.presenter.IUserListPresenter

class UsersRVAdapter(
    private val presenter: IUserListPresenter,
    private val imageLoader: IImageLoader<ImageView>
) : RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersRVAdapter.ViewHolder =
        ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun onBindViewHolder(holder: UsersRVAdapter.ViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    override fun getItemCount(): Int = presenter.getCount()

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root),
        IUserItemView {

        override var pos: Int = -1

        override fun setLogin(text: String) = with(binding) {
            tvLogin.text = text
        }

        override fun loadAvatar(url: String) {
            imageLoader.loadInto(url, binding.avatar)
        }
    }
}