package ru.brauer.mvp.model.convertimagefile

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}