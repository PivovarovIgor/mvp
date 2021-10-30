package ru.brauer.mvp.ui

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.brauer.mvp.model.IImageLoader

class GlideImageLoader : IImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container)
            .load(url)
            .into(container)
    }
}